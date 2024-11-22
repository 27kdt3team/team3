package com.team3.scvs.repository;

import com.team3.scvs.entity.WatchlistStocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchlistStocksRepository extends JpaRepository<WatchlistStocksEntity, Long> {
    List<WatchlistStocksEntity> findByWatchlistId(Long watchlistId);
}
