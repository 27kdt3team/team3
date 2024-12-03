package com.team3.scvs.repository;

import com.team3.scvs.entity.KorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorRepository extends JpaRepository<KorEntity, Long> {
}
