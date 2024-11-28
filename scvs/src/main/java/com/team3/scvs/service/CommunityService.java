package com.team3.scvs.service;

import com.team3.scvs.entity.*;
import com.team3.scvs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityStockInfoRepository communityStockInfoRepository;
    private final CommunityVoteRepository communityVoteRepository;
    private final CommunityRepository communityRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final UserRepository userRepository;
    private final CommunityCommentViewRepository communityCommentViewRepository;

    public CommunityStockInfoEntity getstockinfo(Long tickerId) {
        return communityStockInfoRepository.findById(tickerId).orElse(null);
    }

    public CommunityVoteEntity getVoteInfo(long communityId) {
        // communityId로 CommunityVoteEntity 조회
        CommunityVoteEntity voteInfo = communityVoteRepository.findByCommunity_CommunityId(communityId).orElse(null);
        if (voteInfo == null) {
            // 조회 결과가 없으면 새 엔티티 생성
            voteInfo = new CommunityVoteEntity();
            voteInfo.setCommunity(new CommunityEntity());
            voteInfo.getCommunity().setCommunityId(communityId); // communityId 설정
            voteInfo.setPositiveVotes(0); // 초기값 설정
            voteInfo.setNegativeVotes(0); // 초기값 설정
            // 새로 생성한 엔티티 저장
            voteInfo = communityVoteRepository.save(voteInfo);
        }
        return voteInfo; // 기존 또는 새로 생성된 엔티티 반환
    }


    public CommunityEntity getOrCreateCommunity(Long tickerId) {
        // tickerId로 CommunityEntity 조회
        CommunityEntity community = communityRepository.findByTickerId(tickerId).orElse(null);
        if (community == null) {
            // 조회 결과가 없으면 새 엔티티 생성
            CommunityEntity newCommunity = new CommunityEntity();
            newCommunity.setTickerId(tickerId); // tickerId 설정
            community = communityRepository.save(newCommunity); // 저장 후 반환
        }
        return community; // 조회된 또는 새로 생성된 엔티티 반환
    }

    public List<CommunityCommentViewEntity> getComments(Long communityId) {
        return communityCommentViewRepository.findAllByCommunityId(communityId);
    }

    public void addComment(String commentInput, Long communityId, Long userId) {
        // CommunityEntity 조회
        CommunityEntity community = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 communityId에 대한 커뮤니티가 존재하지 않습니다."));
        // UserEntity 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 userId에 대한 사용자가 존재하지 않습니다."));
        // 새로운 댓글 엔티티 생성 및 설정
        CommunityCommentEntity newComment = new CommunityCommentEntity();
        newComment.setCommunity(community);
        newComment.setUser(user);
        newComment.setComment(commentInput);

        // 댓글 저장
        communityCommentRepository.save(newComment);
    }


}
