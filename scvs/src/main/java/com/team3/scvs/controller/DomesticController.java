package com.team3.scvs.controller;

import com.team3.scvs.dto.DomesticDto;
import com.team3.scvs.dto.ForexDto;
import com.team3.scvs.service.DomesticService;
import com.team3.scvs.service.ForexService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor //자동으로 생성자 생성
public class DomesticController {
    private final DomesticService domesticService;
    private final ForexService forexService;

    @ModelAttribute("isLoggedIn")
    public boolean setDefaultIsLoggedIn() {
        return false; //기본값 설정

    }

    //뉴스 목록 조회
    //url: /domestic?page=1
    @GetMapping("/domestic")
    public String getDomesticList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<DomesticDto> domesticList = domesticService.getDomesticList(pageable); //페이징된 국내 경제 뉴스 데이터 리스트
        ForexDto forex = forexService.getForex(); //환율 데이터

        //페이징
        int blockLimit = 10; //한번에 보여질 페이지 번호의 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < domesticList.getTotalPages()) ? startPage + blockLimit - 1 : domesticList.getTotalPages();

        //모델에 데이터 추가하여 뷰에 전달
        model.addAttribute("domesticList", domesticList); //뉴스 데이터 리스트
        model.addAttribute("startPage", startPage); //첫번째 페이지
        model.addAttribute("endPage", endPage); //마지막 페이지

        model.addAttribute("forex", forex); //환율 데이터

        //뷰 템플릿
        return "News/domestic";

    }

    //뉴스 상세 페이지 조회
    //url: /domestic/1?page=1
    @GetMapping("/domestic/{korEconNewsId}")
    public String findById(@PathVariable Long korEconNewsId, Model model,
                           @PageableDefault(page = 1) Pageable pageable) {
        DomesticDto domesticDto = domesticService.findById(korEconNewsId); //korEconNewsId로 조회한 뉴스 데이터

        //모델에 데이터 추가하여 뷰에 전달
        model.addAttribute("domestic", domesticDto); //뉴스 상세 데이터
        model.addAttribute("page", pageable.getPageNumber()); //현재 페이지

        //뷰 템플릿
        return "News/domesticDetail";

    }

}
