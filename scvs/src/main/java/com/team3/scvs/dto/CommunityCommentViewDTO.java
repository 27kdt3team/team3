package com.team3.scvs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 생성
public class CommunityCommentViewDTO {
    private long communityId; // 커뮤니티 ID
    private String nickname; // 닉네임
    private String comment; // 댓글 내용
    private LocalDateTime publishedAt; // 게시 시간
    private LocalDateTime updatedAt; // 수정 시간
}
