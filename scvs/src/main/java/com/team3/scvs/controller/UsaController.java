package com.team3.scvs.controller;

import com.team3.scvs.dto.UsaDto;
import com.team3.scvs.service.UsaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor //자동으로 생성자 생성
public class UsaController {
    private final UsaService usaService;

    @ModelAttribute("isLoggedIn") //자동으로 설정
    public boolean setDefaultIsLoggedIn() {
        return false; //기본값 설정

    }

    //url: /usa?page=1
    @GetMapping("/usa")
    public String getUsaList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        //서비스 호출 및 모델에 데이터 추가
        Page<UsaDto> usaList = usaService.getUsaList(pageable);

        int blockLimit = 5; //한번에 보여질 페이지 번호의 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < usaList.getTotalPages()) ? startPage + blockLimit - 1 : usaList.getTotalPages();

        model.addAttribute("usaList", usaList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        //렌더링할 html 파일 경로
        return "News/usa";

    }

}
