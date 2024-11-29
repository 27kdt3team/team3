package com.team3.scvs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "forex")
@Getter
@Setter
public class ForexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forexId;

    private String forexName;
    private BigDecimal rate;
    private BigDecimal changeValue;
    private BigDecimal changePercent;
    private LocalDateTime lastUpdated;

}
