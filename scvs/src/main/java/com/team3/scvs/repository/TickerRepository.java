package com.team3.scvs.repository;

import com.team3.scvs.entity.TickerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TickerRepository extends JpaRepository<TickerEntity, Long> {

    // 종목 심볼로 검색
    Page<TickerEntity> findBySymbolContaining(String symbol, Pageable pageable);

    // 회사 이름으로 검색
    Page<TickerEntity> findByCompanyContaining(String company, Pageable pageable);

}
