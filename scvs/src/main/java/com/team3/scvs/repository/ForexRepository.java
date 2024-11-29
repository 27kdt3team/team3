package com.team3.scvs.repository;

import com.team3.scvs.entity.ForexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexRepository extends JpaRepository<ForexEntity, Long> {
    //forex_id 값이 가장 큰 단일 엔티티를 반환
    ForexEntity findFirstByOrderByForexIdDesc();

}
