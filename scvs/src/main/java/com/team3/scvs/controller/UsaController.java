package com.team3.scvs.controller;

import com.team3.scvs.dto.UsaDto;
import com.team3.scvs.service.UsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsaController {
    @ModelAttribute("isLoggedIn") // 자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; // 기본값 설정
    }

    @Autowired
    private UsaService usaService;

    @GetMapping("/usa")
    public String getUsaList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
        Page<UsaDto> currentPage = usaService.getUsaList(PageRequest.of(page - 1, 3));
        model.addAttribute("currentPage", page);
        return "News/usa";
    }

}
