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

@Controller
@RequiredArgsConstructor //자동으로 생성자 생성
public class DomesticController {
    private final DomesticService domesticService;
    private final ForexService forexService;

    @ModelAttribute("isLoggedIn") //자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; //기본값 설정

    }

    //url: /domestic?page=1
    @GetMapping("/domestic")
    public String getDomesticList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<DomesticDto> domesticList = domesticService.getDomesticList(pageable); //국내 경제 뉴스 데이터 리스트
        ForexDto forex = forexService.getForex(); //환율 데이터

        //페이징
        int blockLimit = 10; //한번에 보여질 페이지 번호의 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < domesticList.getTotalPages()) ? startPage + blockLimit - 1 : domesticList.getTotalPages();

        //모델에 데이터 추가
        model.addAttribute("domesticList", domesticList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("forex", forex);

        //렌더링할 html 파일 경로
        return "News/domestic";

    }

}
