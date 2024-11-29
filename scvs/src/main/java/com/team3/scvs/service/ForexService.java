package com.team3.scvs.service;

import com.team3.scvs.dto.ForexDto;
import com.team3.scvs.entity.ForexEntity;
import com.team3.scvs.repository.ForexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForexService {
    private final ForexRepository forexRepository;

    public ForexDto getForex() {
        //엔티티 조회
        ForexEntity forexEntity = forexRepository.findFirstByOrderByForexIdDesc();

        //엔티티를 dto로 변환
        return new ForexDto(
                forexEntity.getForexId(),
                forexEntity.getRate(),
                forexEntity.getChangeValue(),
                forexEntity.getChangePercent(),
                forexEntity.getLastUpdated());

    }

}
