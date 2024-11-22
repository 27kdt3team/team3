package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickers") // DB 테이블 이름 tickers
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TickerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tickerId; // ticker_id와 같습니다.

    private String symbol; // 주식 심볼 (예: AAPL)
    private String company; // 회사명 (예: Apple Inc.)
}
