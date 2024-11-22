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

    @PostMapping("/signup")
    public String signup(UserDTO userDTO) {
        userService.registerUser(userDTO); // 회원가입서비스 호출
        return "redirect:/";
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
    /*
    @GetMapping("/profile")
    public String userDetail(Model model, @ModelAttribute("isLoggedIn") boolean isLoggedIn) {
        if (!isLoggedIn) {
            // 로그인되지 않은 사용자는 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        // 현재 사용자의 정보를 가져온다고 가정 (예: SecurityContextHolder 사용)
        String email = "user@example.com"; // 로그인된 사용자의 이메일
        UserDTO userDTO = userService.getUserByEmail(email); // 서비스에서 사용자 정보 조회
        model.addAttribute("user", userDTO); // 사용자 정보를 뷰로 전달

        return "profile";
    }
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        // 이미 로그인된 사용자라고 가정하고 더미 데이터 생성
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L); // userId 설정
        userDTO.setEmail("example@example.com");
        userDTO.setNickname("맑은나무123");

        model.addAttribute("user", userDTO); // 사용자 정보를 뷰로 전달
        // 로그인 상태 전달 나중에 삭제
        model.addAttribute("isLoggedIn", true);

        return "user/profile";
    }



}
