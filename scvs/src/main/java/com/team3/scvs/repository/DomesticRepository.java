package com.team3.scvs.repository;

import com.team3.scvs.entity.DomesticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticRepository extends JpaRepository<DomesticEntity, Long> {
}
