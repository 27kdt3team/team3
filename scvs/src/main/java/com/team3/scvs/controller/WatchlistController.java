package com.team3.scvs.controller;

import com.team3.scvs.entity.UserWatchlistEntity;
import com.team3.scvs.entity.WatchlistStocksEntity;
import com.team3.scvs.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    // 사용자의 관심 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserWatchlistEntity>> getWatchlistByUser(@PathVariable Long userId) {
        List<UserWatchlistEntity> watchlist = watchlistService.getUserWatchlists(userId);
        return ResponseEntity.ok(watchlist);
    }

    // 관심 목록에 종목 추가
    @PostMapping("/{userId}")
    public ResponseEntity<String> addStockToWatchlist(@PathVariable Long userId, @RequestBody Long tickerId) {
        watchlistService.addStockToWatchlist(userId, tickerId);
        return ResponseEntity.ok("Stock added to watchlist successfully");
    }

    // 관심 목록에서 종목 삭제
    @DeleteMapping("/{watchlistId}")
    public ResponseEntity<String> deleteFromWatchlist(@PathVariable Long watchlistId) {
        watchlistService.deleteWatchlist(watchlistId);
        return ResponseEntity.ok("Watchlist entry deleted successfully");
    }
}
