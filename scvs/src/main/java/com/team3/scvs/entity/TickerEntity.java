package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Table(name = "tickers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TickerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tickerId;

    private String symbol;

    private String company;
}
