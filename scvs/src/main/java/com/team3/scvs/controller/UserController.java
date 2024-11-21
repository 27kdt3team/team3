package com.team3.scvs.controller;

import com.team3.scvs.entity.User;
import com.team3.scvs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public String getUserDetails(@RequestParam("id") Long id, Model model) {
        logger.info("Fetching user details for ID: {}", id);
        userService.getUserById(id).ifPresentOrElse(
                user -> model.addAttribute("user", user),
                () -> model.addAttribute("error", "사용자를 찾을 수 없습니다.")
        );
        // 로그인 상태 전달 나중에 삭제
        model.addAttribute("isLoggedIn", true);
        return "user/user-details";
    }
}
