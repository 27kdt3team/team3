package com.team3.scvs.repository;

import com.team3.scvs.entity.CommunityCommentViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityCommentViewRepository extends JpaRepository<CommunityCommentViewEntity, Long> {

    @Query("SELECT c FROM CommunityCommentViewEntity c WHERE c.communityId = :communityId ORDER BY c.publishedAt DESC")
    List<CommunityCommentViewEntity> findAllByCommunityIdOrderByPublishedAtDesc(@Param("communityId") Long communityId);

}
