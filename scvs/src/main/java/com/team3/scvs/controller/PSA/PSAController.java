package com.team3.scvs.controller.PSA;

import com.team3.scvs.entity.PSA;
import com.team3.scvs.service.PSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;

@Controller
public class PSAController {

    @Autowired
    private PSAService psaService;

    /**
     * 공지사항 목록 조회
     */

    @GetMapping("/PSA-list")
    public String listAllPSAs(
            @RequestParam(defaultValue = "1") int page, // 기본값: 첫 페이지
            @RequestParam(defaultValue = "20") int size, // 기본값: 한 페이지에 20개
            @RequestParam(defaultValue = "") String query, // 검색어 기본값
            Model model) {

        // 공지사항 페이지 데이터 가져오기(작업중)
        Page<PSA> psaPage;
        if (query.isEmpty()) {
            // 검색어가 없는 경우 전체 조회
            psaPage = psaService.findAllPSAsPaginated(page - 1, size);
        } else {
            // 검색어가 있는 경우 제목으로 검색
            psaPage = psaService.searchPSAsByTitlePaginated(query, page - 1, size);
        }

        model.addAttribute("psas", psaPage.getContent()); // 현재 페이지 데이터
        model.addAttribute("currentPage", page); // 사용자에게 보여줄 페이지 번호
        model.addAttribute("totalPages", psaPage.getTotalPages());
        model.addAttribute("query", query); // 검색어 유지
        model.addAttribute("isLoggedIn", false); // 로그인 여부 기본값

        return "PSA/PSA-list";
    }

    /**
     * 공지사항 상세 조회
     */

    @GetMapping("/PSA-detail/{id}")
    public String viewPSADetail(@PathVariable Long id, Model model) {
        // 데이터베이스에서 공지사항 조회
        PSA psa = psaService.findPSAById(id);

        if (psa == null) {
            // 해당 ID의 공지사항이 없는 경우 404 페이지로 리다이렉트
            return "redirect:/error/404";
        }

        // 작성일을 문자열로 변환
        String formattedDate = (psa.getPublishedAt() != null)
                ? psa.getPublishedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                : "작성일 정보 없음";


        // 로그인 상태 기본값 및 관리자 여부 설정
        boolean isLoggedIn = true; // 로그인 여부 확인 로직으로 대체 가능
        boolean isAdmin = true;    // 관리자 여부 확인 로직으로 대체 가능

        // 모델에 데이터 추가
        model.addAttribute("psa", psa);
        model.addAttribute("publishedAt", formattedDate);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);

        return "PSA/PSA-detail"; // Thymeleaf 템플릿 경로
    }

    /**
     * 공지 추가 화면
     */
    @GetMapping("/PSA-add")
    public String viewPSAAddPage(Model model) {
        // 로그인 상태 기본값 설정 (로그인 관련 로직 없이 기본값만 설정)
        boolean isLoggedIn = false; // 로그인 여부 확인 로직 추가 가능
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "PSA/PSA-add"; // Thymeleaf 템플릿 경로
    }

    @PostMapping("/PSA-add")
    public String addPSA(@ModelAttribute PSA psa) {
        // 기존 savePSA 메서드를 호출하여 데이터 저장
        psaService.savePSA(psa);

        // 저장 후 목록 화면으로 리다이렉트
        return "redirect:/PSA-list";
    }

    /**
     * 공지 수정 화면
     */

    @GetMapping("/PSA/update/{id}")
    public String viewUpdatePSAPage(@PathVariable Long id, Model model) {
        PSA psa = psaService.findPSAById(id);
        if (psa == null) {
            return "redirect:/error/404"; // ID에 해당하는 공지가 없으면 404 페이지로 리다이렉트
        }
        // 로그인 상태 기본값 설정
        boolean isLoggedIn = false; // 실제 로그인 여부 확인 로직으로 대체
        model.addAttribute("isLoggedIn", isLoggedIn);

        model.addAttribute("psa", psa);
        return "PSA/PSA-update"; // 수정 화면 템플릿
    }

    @PostMapping("/PSA/update/{id}")
    public String updatePSA(@PathVariable Long id, @RequestParam String title, @RequestParam String content) {
        PSA psa = psaService.findPSAById(id);
        if (psa != null) {
            psa.setTitle(title);
            psa.setContent(content);
            psaService.savePSA(psa); // 수정된 내용 저장
        }
        return "redirect:/PSA-detail/" + id; // 수정 후 상세 화면으로 리다이렉트
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
