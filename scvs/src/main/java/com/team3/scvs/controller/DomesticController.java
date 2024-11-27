package com.team3.scvs.controller;

import com.team3.scvs.dto.DomesticDto;
import com.team3.scvs.service.DomesticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DomesticController {
    @ModelAttribute("isLoggedIn") //자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; //기본값 설정

    }

    @Autowired
    private DomesticService domesticService;

    @GetMapping("/domestic")
    public String getDomesticList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        int pageSize = 10; //한 페이지 내 기사의 최대 갯수

        Page<DomesticDto> currentPage = domesticService.getDomesticList(PageRequest.of(page, pageSize));

        model.addAttribute("domesticList", currentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", currentPage.getTotalPages());

        return "News/domestic";

    }

}
