package com.team3.scvs.controller;

import com.team3.scvs.dto.UserDTO;
import com.team3.scvs.service.AdminUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminUserService adminUserService;
    // 생성자 주입
    public AdminController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    // 유저리스트 전체 불러오기
    @GetMapping("/userList")
    public String getUserListPage(
            @RequestParam(value = "filterDropdown", defaultValue = "email") String filter,
            @RequestParam(value = "findInput", defaultValue = "") String input,
            @PageableDefault(page = 0, size = 15, sort = "userId",direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ){
        Page<UserDTO> users = adminUserService.getFilteredUsers(filter, input, pageable); // 필터, 검색, 페이징을 통한 검색

        // 처음, 끝페이징
        int startPage = users.getNumber() > (Math.max(users.getTotalPages() - 3, 0)) ? users.getTotalPages() - 5 : Math.max(0, users.getNumber() - 2);
        int endPage = users.getNumber() < 2 ? (Math.min(users.getTotalPages() - 1, 4)) : Math.min(users.getTotalPages() - 1, users.getNumber() + 2);

        // 모델에 결과 담기
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("users", users);
        return  "Admin/userList";
    }


    // 유저리스트 검색해서 불러오기
    @PostMapping("/userList")
    public String getUserList(
            @RequestParam("filterDropdown") String filter,
            @RequestParam("findInput") String input,
            @PageableDefault(page = 0, size = 15, sort = "userId",direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {

        Page<UserDTO> users = adminUserService.getFilteredUsers(filter, input, pageable); // 필터, 검색, 페이징을 통한 검색

        // 처음, 끝페이징
        int startPage = users.getNumber() > (Math.max(users.getTotalPages() - 3, 0)) ? users.getTotalPages() - 5 : Math.max(0, users.getNumber() - 2);
        int endPage = users.getNumber() < 2 ? (Math.min(users.getTotalPages() - 1, 4)) : Math.min(users.getTotalPages() - 1, users.getNumber() + 2);
        // 모델에 결과 담기
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("users", users);
        model.addAttribute("filterDropdown", filter);
        model.addAttribute("findInput", input);

        // 결과를 보여줄 뷰로 이동
        return "Admin/userList"; // Thymeleaf 템플릿 경로
    }

    // 체크한 유저 삭제
    @PostMapping("/deleteUserList")
    public String deleteUserList(
            @RequestParam(name = "userIds", required = false) List<Long> userIds,
            @RequestParam("filterDropdown") String filter,
            @RequestParam("findInput") String input,
            @PageableDefault(page = 0, size = 15, sort = "userId",direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ){
        // user삭제
        adminUserService.deleteUsers(userIds);

        Page<UserDTO> users = adminUserService.getFilteredUsers(filter, input, pageable); // 필터, 검색, 페이징을 통한 검색

        // 처음, 끝페이징
        int startPage = users.getNumber() > (Math.max(users.getTotalPages() - 3, 0)) ? users.getTotalPages() - 5 : Math.max(0, users.getNumber() - 2);
        int endPage = users.getNumber() < 2 ? (Math.min(users.getTotalPages() - 1, 4)) : Math.min(users.getTotalPages() - 1, users.getNumber() + 2);
        // 모델에 결과 담기
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("users", users);
        model.addAttribute("filterDropdown", filter);
        model.addAttribute("findInput", input);
        return "Admin/userList";
    }


    // 관심종목 관리 관련 기능





}
