package com.team3.scvs.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForexDto {
    private Long forexId;
    private BigDecimal rate;
    private BigDecimal changeValue;
    private BigDecimal changePercent;
    private LocalDateTime lastUpdated;

}
