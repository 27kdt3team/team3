package com.team3.scvs.service;

import com.team3.scvs.dto.ForexDTO;
import com.team3.scvs.entity.ForexEntity;
import com.team3.scvs.repository.ForexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForexService {

    @Autowired
    private ForexRepository forexRepository;

    private final String krwUsdForexName = "KRW/USD";

    public ForexDTO getKrwUsdForex(){
        return forexRepository.findByForexName(krwUsdForexName)
                .map(forex -> new ForexDTO(
                    forex.getForexName(),
                    forex.getRate(),
                    forex.getChangeValue(),
                    forex.getChangePercent(),
                    forex.getLastUpdated()
        )).get();
    }
}
