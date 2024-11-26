package com.team3.scvs.controller.PSA;

import com.team3.scvs.entity.PSA;
import com.team3.scvs.service.PSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PSAController {

    @Autowired
    private PSAService psaService;

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/PSA-list")
    public String getPSAList(Model model) {
        // 데이터베이스에서 공지사항 목록 조회
        List<PSA> psaList = psaService.findAllPSAs();

        // 모델에 데이터 추가
        model.addAttribute("psas", psaList);
        model.addAttribute("isLoggedIn", false); // 로그인 상태 기본값 false

        return "PSA/PSA-list"; // 템플릿 경로
    }

    /**
     * 공지사항 상세 조회
     */

    /*
    @GetMapping("/PSA-detail/{id}")
    public String viewPSADetail(@PathVariable Long id, Model model) {
        // 더미 데이터 전달 (하드코딩 없이 화면만 출력)
        model.addAttribute("psa", new Object()); // 더미 데이터
        model.addAttribute("isAdmin", false);   // 관리자 여부 (하드코딩)

        return "PSA/PSA-detail";
    }

     */

    @GetMapping("/PSA-list")
    public String listAllPSAs(
            @RequestParam(defaultValue = "0") int page, // 기본값: 첫 페이지
            @RequestParam(defaultValue = "20") int size, // 기본값: 한 페이지에 20개
            Model model) {

        // 공지사항 페이지 데이터 가져오기(작업중)
        Page<PSA> psaPage = psaService.findAllPSAsPaginated(page, size);

        model.addAttribute("psas", psaPage.getContent()); // 현재 페이지 데이터
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", psaPage.getTotalPages());
        model.addAttribute("isLoggedIn", false); // 로그인 여부 기본값

        return "PSA/PSA-list";
    }

    /**
     * 관리자 권한 여부 확인
     */
    private boolean checkIfAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        }

        return false;
    }
}
