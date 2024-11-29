package com.team3.scvs.service;

import com.team3.scvs.entity.*;
import com.team3.scvs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityService {



    private final CommunityStockInfoRepository communityStockInfoRepository;
    private final CommunityVoteRepository communityVoteRepository;
    private final CommunityRepository communityRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final UserRepository userRepository;
    private final CommunityCommentViewRepository communityCommentViewRepository;
    private final UserVoteRepository userVoteRepository;
    private final StocksRepository stocksRepository;
    private final StocksNewsRepository stocksNewsRepository;

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

    public boolean castVote(Long communityId, Long userId, String voteType) {
        // CommunityVoteEntity 조회 또는 생성
        CommunityVoteEntity voteInfo = getVoteInfo(communityId);

        // UserVoteEntity 조회 또는 생성
        UserVoteEntity userVote = userVoteRepository.findByUserIdAndCommunityId(userId, communityId)
                .orElseGet(() -> {
                    // UserVoteEntity가 없으면 새로 생성
                    UserVoteEntity newUserVote = new UserVoteEntity();
                    newUserVote.setUserId(userId);
                    newUserVote.setCommunityId(communityId);
                    newUserVote.setVote(false); // 기본값 false
                    newUserVote.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정
                    return userVoteRepository.save(newUserVote);
                });
        if (userVote.isVote()) {
            return false; // 투표 실패
        }

        // 투표 데이터 업데이트
        switch (voteType.toLowerCase()) {
            case "positive":
                voteInfo.setPositiveVotes(voteInfo.getPositiveVotes() + 1);
                break;
            case "negative":
                voteInfo.setNegativeVotes(voteInfo.getNegativeVotes() + 1);
                break;
            default:
                throw new IllegalArgumentException("올바르지 않은 투표 유형입니다: " + voteType);
        }

        // 변경된 투표 데이터 저장
        userVote.setVote(true);
        userVoteRepository.save(userVote);
        communityVoteRepository.save(voteInfo);

        return true; // 성공적으로 업데이트되었음을 반환
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
        return communityCommentViewRepository.findAllByCommunityIdOrderByPublishedAtDesc(communityId);
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
    public boolean deleteComment(Long communityCommentId, Long userId) {
        // 댓글 조회
        CommunityCommentEntity comment = communityCommentRepository.findById(communityCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 댓글 작성자와 로그인 사용자 일치 여부 확인
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new SecurityException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        // 댓글 삭제
        communityCommentRepository.delete(comment);
        return true; // 성공적으로 삭제되었음을 반환
    }

    public boolean editComment(Long commentId, Long userId, String updatedComment) {
        // 댓글 조회
        CommunityCommentEntity comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 댓글 작성자와 로그인 사용자 일치 여부 확인
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new SecurityException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        // 댓글 내용 업데이트
        comment.setComment(updatedComment);
        comment.setUpdatedAt(LocalDateTime.now()); // 수정 시간 갱신
        communityCommentRepository.save(comment);

        return true; // 성공적으로 수정되었음을 반환
    }

    public Optional<StocksEntity> getStocksinfo(Long tickerId) {
        return stocksRepository.findById(tickerId);
    }
    public List<StocksNewsEntity> getStocksNewstitle(Long tickerId){
        return stocksNewsRepository.findLatestByTickerId(tickerId).stream().limit(5).toList();

    }

}
