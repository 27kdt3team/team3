package com.team3.scvs.controller;

import com.team3.scvs.entity.StocksEntity;
import com.team3.scvs.entity.TickerEntity;
import com.team3.scvs.service.StocksService;
import com.team3.scvs.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@Controller
public class StockWatchController {

    @Autowired
    private StocksService stocksService;

    @GetMapping("/stockwatch")
    public String stockwatch(@RequestParam("tickerId") Long tickerId,
             Model model) {
        //회사 정보 가져오기
        List<TickerEntity> tickerinfo = stocksService.gettickerinfo(tickerId);
        //현재 가격 가져오기
        BigDecimal currentPrice = stocksService.getStockcurrent(tickerId);
        //마켓 정보 가져오기
        List<StocksEntity> stockinfo = stocksService.getStocksinfo(tickerId);

        // 로그인 구현되면 삭제
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("tickerinfo",tickerinfo);
        model.addAttribute("currentPrice",currentPrice);
        model.addAttribute("stockinfo",stockinfo);
        return "Stockwatch/stockwatch"; // 경로는 templates 폴더 내 위치 기준
    }
}