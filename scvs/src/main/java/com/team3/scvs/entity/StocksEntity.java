package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;


@Entity
@Data
@Immutable //읽기전용 Entity
@NoArgsConstructor  // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 생성
@Table(name = "vw_stocks")
public class StocksEntity {
    @Id
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id")
    private TickerEntity tickerId;

    private String market;
    private BigDecimal currentPrice;
    private BigDecimal close;
    private BigDecimal open;
    private Long volume;
    private BigDecimal fiftytwoWeekLow;
    private BigDecimal fiftytwoWeekHigh;
    private BigDecimal dayLow;
    private BigDecimal dayHigh;
    private BigDecimal returnOnAssets;
    private BigDecimal returnOnEquity;
    private BigDecimal enterpriseValue;
    private BigDecimal enterpriseToEBITDA;
    private BigDecimal priceToBook;
    private BigDecimal priceToSales;
    private BigDecimal earningsPerShare;
    private BigDecimal currentRatio;
    private BigDecimal debtToEquity;

}
