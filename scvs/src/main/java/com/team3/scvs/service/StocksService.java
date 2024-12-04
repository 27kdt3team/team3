package com.team3.scvs.service;

import com.team3.scvs.entity.StocksEntity;
import com.team3.scvs.entity.TickerEntity;
import com.team3.scvs.repository.StocksRepository;
import com.team3.scvs.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StocksService {
    @Autowired
    private final TickerRepository tickerRepository;
    @Autowired
    private StocksRepository stocksRepository;

    public List<TickerEntity> gettickerinfo(Long tickerId) {
        Optional<TickerEntity> tickerinfo = tickerRepository.findById(tickerId);
        //Optional 리스트를 일반 리스트로 바꾸는 과정 값이 없으면 빈 리스트
        return tickerinfo.map(Collections::singletonList).orElse(Collections.emptyList());
    }
    public BigDecimal getStockcurrent(Long tickerId){
        Optional<StocksEntity> stockEntityOptional = stocksRepository.findById(tickerId);
        return stockEntityOptional.get().getCurrentPrice();
    }
    public List<StocksEntity> getStocksinfo(Long tickerId) {
        Optional<StocksEntity> stocksinfo = stocksRepository.findById(tickerId);
        return stocksinfo.map(Collections::singletonList).orElse(Collections.emptyList());
    }
}
