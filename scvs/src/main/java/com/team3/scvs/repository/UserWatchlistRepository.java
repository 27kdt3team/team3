package com.team3.scvs.repository;

import com.team3.scvs.entity.UserWatchlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWatchlistRepository extends JpaRepository<UserWatchlistEntity, Long> {
    List<UserWatchlistEntity> findByUserId(Long userId);

}
