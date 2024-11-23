package com.team3.scvs.service;

import com.team3.scvs.entity.TickerEntity;
import com.team3.scvs.entity.UserWatchlistEntity;
import com.team3.scvs.entity.WatchlistStocksEntity;
import com.team3.scvs.repository.TickerRepository;
import com.team3.scvs.repository.UserWatchlistRepository;
import com.team3.scvs.repository.WatchlistStocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final UserWatchlistRepository userWatchlistRepository;
    private final WatchlistStocksRepository watchlistStocksRepository;
    private final TickerRepository tickerRepository;

    //전체 리스트
    public List<TickerEntity> getAllTickers() {
        return tickerRepository.findAll();
    }

    //관심 목록 리스트
    public List<UserWatchlistEntity> getUserWatchlists(Long userId) {
        return userWatchlistRepository.findByUserId(userId);
    }

    public UserWatchlistEntity getOrCreateWatchlist(Long userId, Long watchlistId) {
        return userWatchlistRepository.findById(watchlistId)
                .orElseGet(() -> {
                    UserWatchlistEntity newWatchlist = new UserWatchlistEntity();
                    newWatchlist.setUserId(userId);
                    return userWatchlistRepository.save(newWatchlist);
                });
    }

    public void addStockToWatchlist(Long userWatchlistId, Long tickerId) {
        // 관심 목록 조회
        UserWatchlistEntity userWatchlist = userWatchlistRepository.findById(userWatchlistId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid watchlist ID: " + userWatchlistId));

        // 티커 조회
        TickerEntity ticker = tickerRepository.findById(tickerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticker ID: " + tickerId));

        // 관심 목록에 종목이 이미 있는지 확인하고 없을 때만 추가
        boolean stockExists = watchlistStocksRepository.existsByUserWatchlistAndTicker(userWatchlist, ticker);
        if (stockExists) {
            throw new IllegalArgumentException("Stock already exists in the watchlist.");
        }

        // 새로운 관심 종목 엔티티 생성 및 저장
        WatchlistStocksEntity watchlistStock = WatchlistStocksEntity.builder()
                .userWatchlist(userWatchlist)
                .ticker(ticker)
                .build();

        watchlistStocksRepository.save(watchlistStock);
    }


    //관심 목록 정보 가져오기
    public List<TickerEntity> getTickersByUserId(Long userId) {
        List<UserWatchlistEntity> watchlists = userWatchlistRepository.findByUserId(userId);
        return watchlists.stream()
                .flatMap(watchlist -> watchlist.getStocks().stream())
                .map(WatchlistStocksEntity::getTicker)
                .toList();
    }
    //관심 목록 제거
    public void deleteStockFromWatchlist(Long watchlistId, Long tickerId) {
        // 특정 관심 목록에서 지정된 티커 삭제
        WatchlistStocksEntity watchlistStock = (WatchlistStocksEntity) watchlistStocksRepository.findByUserWatchlistUserWatchlistIdAndTickerTickerId(watchlistId, tickerId)
                .orElseThrow(() -> new IllegalArgumentException("Watchlist or Ticker not found"));
        watchlistStocksRepository.delete(watchlistStock);
    }
    //watchlistId 설정
    public Long getNextWatchlistId(Long userId) {
        List<UserWatchlistEntity> watchlists = userWatchlistRepository.findByUserId(userId);
        if (watchlists.isEmpty()) {
            return 1L; // 관심 목록이 없으면 기본값으로 1
        } else {
            return watchlists.stream()
                    .mapToLong(UserWatchlistEntity::getUserWatchlistId)
                    .max()
                    .orElse(0L) + 1L; // 가장 큰 ID에 1을 더해 반환
        }
    }
}
