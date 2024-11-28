package com.team3.scvs.controller;

import com.team3.scvs.dto.DomesticDto;
import com.team3.scvs.service.DomesticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor //자동으로 생성자 생성
@RequestMapping("/domestic")
public class DomesticController {
    private final DomesticService domesticService;

    @ModelAttribute("isLoggedIn") //자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; //기본값 설정

    }

    /*
    //국내 경제 뉴스 리스트 보여주기
    @GetMapping("")
    public String findAll(Model model) {
        //DB에서 뉴스 데이터들을 가져와서 domestic.html에 보여준다.
        List<DomesticDto> domesticDtoList = domesticService.findAll();
        model.addAttribute("domesticList", domesticDtoList);

        return "News/domestic";
    }
    */

    @GetMapping("")
    public String getDomesticList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        int pageSize = 10; //페이지당 뉴스 개수

        //페이지 요청 생성
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);

        //서비스 호출 및 모델에 데이터 추가
        Page<DomesticDto> currentPage = domesticService.getDomesticList(pageRequest);

        model.addAttribute("domesticList", currentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", currentPage.getTotalPages());

        //html 파일 경로로 렌더링
        return "News/domestic";

    }

}
