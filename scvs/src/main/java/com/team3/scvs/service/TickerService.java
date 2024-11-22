package com.team3.scvs.service;

import com.team3.scvs.entity.TickerEntity;
import com.team3.scvs.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerService {

    private final TickerRepository tickerRepository;

    // 심볼 또는 회사 이름으로 종목 검색
    public List<TickerEntity> searchTickers(String keyword) {
        return tickerRepository.findBySymbolContainingOrCompanyContaining(keyword, keyword);
    }

    // 모든 종목 조회
    public List<TickerEntity> getAllTickers() {
        return tickerRepository.findAll();
    }
}
