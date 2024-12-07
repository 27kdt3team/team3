package com.team3.scvs.controller;

import com.team3.scvs.dto.TickerDTO;
import com.team3.scvs.service.AdminWatchListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWatchListController {

    private final AdminWatchListService adminWatchListService;

    // 생성자 주입
    public AdminWatchListController(AdminWatchListService adminWatchListService) {
        this.adminWatchListService = adminWatchListService;
    }

    // 전체 관심 종목 불러오기
    @GetMapping("/watchList")
    public String getWatchListPage(
            @RequestParam(value = "filterDropdown", defaultValue = "symbol") String filter,
            @RequestParam(value = "findInput", defaultValue = "") String input,
            @PageableDefault(page = 0, size = 15, sort = "tickerId", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<TickerDTO> tickers = adminWatchListService.getFilteredTickers(filter, input, pageable);

        // 페이징 계산
        int startPage = tickers.getNumber() > Math.max(tickers.getTotalPages() - 3, 0) ? tickers.getTotalPages() - 5 : Math.max(0, tickers.getNumber() - 2);
        int endPage = tickers.getNumber() < 2 ? Math.min(tickers.getTotalPages() - 1, 4) : Math.min(tickers.getTotalPages() - 1, tickers.getNumber() + 2);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("tickers", tickers);
        model.addAttribute("filterDropdown", filter);
        model.addAttribute("findInput", input);

        return "Admin/watchList"; // HTML 템플릿 경로
    }

    // 관심 종목 추가
    @PostMapping("/watchList/add")
    public String addTickerToWatchList(@RequestParam("tickerId") Long tickerId) {
        adminWatchListService.addTicker(tickerId);
        return "redirect:/admin/watchList";
    }

    // 관심 종목 삭제
    @PostMapping("/watchList/delete")
    public String deleteTickerFromWatchList(@RequestParam(name = "tickerIds", required = false) List<Long> tickerIds) {
        adminWatchListService.deleteTickers(tickerIds);
        return "redirect:/admin/watchList";
    }
}

