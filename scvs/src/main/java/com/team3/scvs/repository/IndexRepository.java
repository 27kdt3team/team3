package com.team3.scvs.repository;

import com.team3.scvs.entity.IndicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndexRepository extends JpaRepository<IndicesEntity, Long> {
    Optional<IndicesEntity> findByTitle(String title);
}
