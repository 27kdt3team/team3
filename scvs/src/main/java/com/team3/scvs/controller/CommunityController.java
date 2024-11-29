package com.team3.scvs.controller;


import com.team3.scvs.entity.*;
import com.team3.scvs.service.CommunityService;
import com.team3.scvs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;


@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/community")
    public String community(@RequestParam("tickerId") Long tickerId,
                             Model model) {
        //로그인 기능이 완성되면 호출
        //Long userId = customUserDetailsService.getLoggedInUserId();

        //로그인 기능 사라지면 지우기
        long userId = 1;

        //주식 정보 가져오기
        CommunityStockInfoEntity stockInfo = communityService.getstockinfo(tickerId);
        // 커뮤니티 테이블 가져오기
        CommunityEntity community = communityService.getOrCreateCommunity(tickerId);
        //투표 결과 가져오기
        Long communityId = community.getCommunityId(); // Community ID를 추출
        CommunityVoteEntity voteInfo = communityService.getVoteInfo(communityId);
        //토론방 코멘트 가져오기
        List<CommunityCommentViewEntity> commentList = communityService.getComments(communityId);
        //마켓 정보 가져오기
        Optional<StocksEntity> stockinfoOptional = communityService.getStocksinfo(tickerId);
        StocksEntity stockinfo = stockinfoOptional.orElse(null); // 값이 없으면 null을 반환
        //뉴스 제목 가져오기
        List<StocksNewsEntity> stocknewsinfo = communityService.getStocksNewstitle(tickerId);

        model.addAttribute("userId",userId);
        model.addAttribute("stockInfo", stockInfo);
        model.addAttribute("voteInfo",voteInfo);
        model.addAttribute("commentList",commentList);
        model.addAttribute("communityId",communityId);
        model.addAttribute("stockinfo",stockinfo);
        model.addAttribute("stocknewsinfo",stocknewsinfo);
        return "Stockwatch/community";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam("CommunityId") Long communityId,
                             @RequestParam("tickerId") Long tickerId,
                             @RequestParam("commentInput") String commentInput) {
        //로그인 기능이 완성되면 호출
        //Long userId = customUserDetailsService.getLoggedInUserId();

        //로그인 기능 사라지면 지우기
        long userId = 1;

        // 댓글 등록 로직 호출
        communityService.addComment(commentInput, communityId, userId);

        // 등록 후 기존 페이지로 리다이렉트
        return "redirect:/community?tickerId=" + tickerId;
    }

    @PostMapping("/castVote")
    public String castVote(@RequestParam("communityId") Long communityId,
                           @RequestParam("voteType") String voteType,
                           @RequestParam("tickerId") Long tickerId,
                           Model model) {
        //로그인 기능이 완성되면 호출
        //Long userId = customUserDetailsService.getLoggedInUserId();

        //로그인 기능 사라지면 지우기
        long userId = 1;

        try {
            // 투표 처리 로직 호출
            boolean isVoteSuccessful = communityService.castVote(communityId, userId, voteType);

            if (!isVoteSuccessful) {
                // 이미 투표한 경우 오류 메시지 추가
                model.addAttribute("voteError", "이미 투표하셨습니다.");
            }
        } catch (IllegalArgumentException e) {
            // 잘못된 입력 처리
            model.addAttribute("voteError", e.getMessage());
        }

        // 기존 페이지로 리다이렉트
        return "redirect:/community?tickerId=" + tickerId;
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("communityCommentId") Long communityCommentId,
                                @RequestParam("tickerId") Long tickerId,
                                Model model) {
        //로그인 기능이 완성되면 호출
        //Long userId = customUserDetailsService.getLoggedInUserId();

        // 로그인된 사용자 ID 가져오기 (임시)
        Long userId = 1L; // 실제 로그인 기능 구현 후 교체
        try {
            // 삭제 로직 호출
            communityService.deleteComment(communityCommentId, userId);
        } catch (SecurityException e) {
            model.addAttribute("deleteError", e.getMessage());
        } catch (IllegalArgumentException e) {
            model.addAttribute("deleteError", "댓글을 찾을 수 없습니다.");
        }

        // 삭제 후 기존 페이지로 리다이렉트
        return "redirect:/community?tickerId=" + tickerId;
    }
    @PostMapping("/editComment")
    public String editComment(@RequestParam("communityCommentId") Long commentId,
                              @RequestParam("tickerId") Long tickerId,
                              @RequestParam("editedComment") String updatedComment,
                              Model model) {

        //로그인 기능이 완성되면 호출
        //Long userId = customUserDetailsService.getLoggedInUserId();

        // 로그인된 사용자 ID 가져오기 (임시)
        Long userId = 1L; // 실제 로그인 기능 구현 후 교체

        try {
            // 수정 로직 호출
            communityService.editComment(commentId, userId, updatedComment);
        } catch (SecurityException e) {
            model.addAttribute("editError", e.getMessage());
        } catch (IllegalArgumentException e) {
            model.addAttribute("editError", "댓글을 찾을 수 없습니다.");
        }

        // 수정 후 기존 페이지로 리다이렉트
        return "redirect:/community?tickerId=" + tickerId;
    }


//    @GetMapping("/domestic/id")
//    public String getNewsDetail(@PathVariable("id") String id, Model model) {
//        // ID에 해당하는 뉴스 데이터를 가져오기
//        NewsDTO news = newsService.getNewsById(id);
//        model.addAttribute("newsDetail", news);
//        return "news-detail"; // 상세 페이지를 보여줄 Thymeleaf 템플릿 이름
//    }

}
