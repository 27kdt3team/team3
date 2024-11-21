package com.team3.scvs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String mainPage(Model model) {
        // TODO: 로그인 여부는 임시로 false를 줌. 추후 수정 예정
        boolean isLoggedIn = false;
        model.addAttribute("isLoggedIn", isLoggedIn);
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

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
