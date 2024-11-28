package com.team3.scvs.repository;

import com.team3.scvs.entity.CommunityCommentViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentViewRepository extends JpaRepository<CommunityCommentViewEntity, Long> {

    List<CommunityCommentViewEntity> findAllByCommunityId(Long communityId);
}
