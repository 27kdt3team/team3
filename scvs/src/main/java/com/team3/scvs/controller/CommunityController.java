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


@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/community")
    public String community(@RequestParam("tickerId") Long tickerId,
                             Model model) {
        //주식 정보 가져오기
        CommunityStockInfoEntity stockInfo = communityService.getstockinfo(tickerId);
        // 커뮤니티 테이블 가져오기
        CommunityEntity community = communityService.getOrCreateCommunity(tickerId);
        //투표 결과 가져오기
        Long communityId = community.getCommunityId(); // Community ID를 추출
        CommunityVoteEntity voteInfo = communityService.getVoteInfo(communityId);
        //토론방 코멘트 가져오기
        List<CommunityCommentViewEntity> commentList = communityService.getComments(communityId);
        // 로그인 구현되면 삭제
        model.addAttribute("isLoggedIn", true);

        model.addAttribute("stockInfo", stockInfo);
        model.addAttribute("voteInfo",voteInfo);
        model.addAttribute("commentList",commentList);
        model.addAttribute("communityId",communityId);
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


}
