package com.team3.scvs.repository;

import com.team3.scvs.entity.UsaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsaRepository extends JpaRepository<UsaEntity, Long> {
}
