package com.team3.scvs.service;

import com.team3.scvs.entity.UserWatchlistEntity;
import com.team3.scvs.entity.WatchlistStocksEntity;
import com.team3.scvs.repository.UserWatchlistRepository;
import com.team3.scvs.repository.WatchlistStocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final UserWatchlistRepository userWatchlistRepository;
    private final WatchlistStocksRepository watchlistStocksRepository;

    // 사용자의 관심 목록 조회
    public List<UserWatchlistEntity> getUserWatchlists(Long userId) {
        return userWatchlistRepository.findByUserId(userId);
    }

    // 관심 목록에 종목 추가
    public void addStockToWatchlist(Long userId, Long tickerId) {
        // 관심 목록이 이미 존재하는지 확인
        UserWatchlistEntity watchlist = userWatchlistRepository.findByUserId(userId)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    UserWatchlistEntity newWatchlist = new UserWatchlistEntity();
                    newWatchlist.setUserId(userId);
                    return userWatchlistRepository.save(newWatchlist);
                });

        // 관심 목록에 종목 추가
        WatchlistStocksEntity watchlistStock = new WatchlistStocksEntity();
        watchlistStock.setWatchlistId(watchlist.getUserWatchlistId());
        watchlistStock.setTickerId(tickerId);
        watchlistStocksRepository.save(watchlistStock);
    }

    // 관심 목록에서 종목 삭제
    public void deleteWatchlist(Long watchlistId) {
        // 관심 목록에 연결된 종목 삭제
        List<WatchlistStocksEntity> stocks = watchlistStocksRepository.findByWatchlistId(watchlistId);
        watchlistStocksRepository.deleteAll(stocks);

        // 관심 목록 삭제
        userWatchlistRepository.deleteById(watchlistId);
    }
}
