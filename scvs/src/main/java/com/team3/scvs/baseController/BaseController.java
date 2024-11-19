package com.team3.scvs.baseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String mainPage(Model model) {
        boolean isLoggedIn = false;
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "main";
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
