package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "watchlist_stocks") // DB 테이블 이름 watchlist_stocks
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WatchlistStocksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long watchlistStockId; // watchlist_stock_id와 같습니다.

    private Long watchlistId; // user_watchlist 테이블의 user_watchlist_id와 연결
    private Long tickerId; // tickers 테이블의 ticker_id와 연결
}
