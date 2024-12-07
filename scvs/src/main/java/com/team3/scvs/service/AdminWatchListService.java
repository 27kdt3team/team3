package com.team3.scvs.service;

import com.team3.scvs.dto.TickerDTO;
import com.team3.scvs.entity.TickerEntity;
import com.team3.scvs.repository.TickerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminWatchListService {

    private final TickerRepository tickerRepository;

    // 생성자 주입
    public AdminWatchListService(TickerRepository tickerRepository) {
        this.tickerRepository = tickerRepository;
    }

    // convert (TickerEntity >> TickerDTO)
    private TickerDTO convertToDTO(TickerEntity tickerEntity) {
        return new TickerDTO(
                tickerEntity.getTickerId(),
                tickerEntity.getSymbol(),
                tickerEntity.getCompany()
        );
    }

    // 필터 및 검색 처리
    public Page<TickerDTO> getFilteredTickers(String filter, String input, Pageable pageable) {
        if (input != null && input.trim().isEmpty()) {
            return getAllTickers(pageable); // 입력값이 비어있으면 전체 조회
        }

        if ("symbol".equals(filter)) {
            // 종목 심볼로 검색
            return tickerRepository.findBySymbolContaining(input, pageable).map(this::convertToDTO);
        } else if ("company".equals(filter)) {
            // 회사 이름으로 검색
            return tickerRepository.findByCompanyContaining(input, pageable).map(this::convertToDTO);
        } else {
            return getAllTickers(pageable); // 필터 없이 전체 조회
        }
    }

    // 전체 티커 조회
    public Page<TickerDTO> getAllTickers(Pageable pageable) {
        return tickerRepository.findAll(pageable).map(this::convertToDTO);
    }

    // 관심 종목 추가
    public void addTicker(Long tickerId) {
        TickerEntity tickerEntity = tickerRepository.findById(tickerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticker ID: " + tickerId));

        // 추가 로직 필요 시 구현
    }

    // 관심 종목 삭제
    public void deleteTickers(List<Long> tickerIds) {
        if (tickerIds != null && !tickerIds.isEmpty()) {
            tickerRepository.deleteAllById(tickerIds);
        }
    }
}

