package com.team3.scvs.controller;

import com.team3.scvs.dto.UserDTO;
import com.team3.scvs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Controller
public class UserController {

    @ModelAttribute("isLoggedIn") // 자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; // 기본값 설정
    }


    @Autowired
    private UserService userService;


    @GetMapping("/signup")
    public String signupView(Model model) {
        UserDTO userDTO = new UserDTO();
        String randomNickname = userService.generateRandomNickname(); // 랜덤 닉네임 생성
        userDTO.setNickname(randomNickname);    // 닉네임 초기값 세팅

        model.addAttribute("userDTO", userDTO); // 회원
        return "Account/signup";
    }
    @GetMapping("/signupSuccess")
    public String showLoginSuccessPage() {
        return "Account/signupSuccess";  // loginSuccess.html을 반환
    }

    @PostMapping("/signup")
    public String signup(UserDTO userDTO) {
        userService.registerUser(userDTO); // 회원가입서비스 호출
        return "redirect:/signupSuccess";
    }

    @PostMapping("/api/check-email")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody Map<String, String> request) {
        String email = request.get("email"); // 클라이언트에서 전송한 이메일
        boolean isDuplicate = userService.isEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }

    @PostMapping("/api/check-nickname")
    public ResponseEntity<Boolean> checknicknameDuplicate(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname"); // 클라이언트에서 전송한 이메일
        boolean isDuplicate = userService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok(isDuplicate);
    }
}
