package com.team3.scvs.controller;

import com.team3.scvs.dto.ForexDTO;
import com.team3.scvs.dto.IndexDTO;
import com.team3.scvs.service.ForexService;
import com.team3.scvs.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ForexService forexService;

    @ModelAttribute("isLoggedIn") // 자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; // 기본값 설정
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        List<IndexDTO> indices = indexService.getIndices();
        ForexDTO krwUsdForex = forexService.getKrwUsdForex();

        model.addAttribute("indices", indices);
        model.addAttribute("forex", krwUsdForex);
        return "index";
    }

    @GetMapping("/domestic")
    public String domesticPage() {
        return "domestic";
    }

    @GetMapping("/usa")
    public String usaPage() {
        return "usa";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "/Account/login";
    }
}
