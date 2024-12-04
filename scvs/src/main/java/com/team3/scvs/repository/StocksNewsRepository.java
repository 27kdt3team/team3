package com.team3.scvs.repository;

import com.team3.scvs.entity.StocksEntity;
import com.team3.scvs.entity.StocksNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksNewsRepository extends JpaRepository<StocksNewsEntity, String> {

    @Query("SELECT s FROM StocksNewsEntity s WHERE s.tickerId = :tickerId ORDER BY s.publishedAt DESC")
    List<StocksNewsEntity> findLatestByTickerId(@Param("tickerId") long tickerId);

}
