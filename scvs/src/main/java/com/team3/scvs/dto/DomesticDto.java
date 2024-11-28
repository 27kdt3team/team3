package com.team3.scvs.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DomesticDto {
    private Long korEconNewsId;
    private String title;
    private String source;
    private String imageLink;
    private String content;
    private LocalDateTime publishedAt;
    private String link;

    //뉴스 리스트 생성자
    public DomesticDto(Long korEconNewsId, String title, String source, LocalDateTime publishedAt, String imageLink) {
        this.korEconNewsId = korEconNewsId;
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;

    }

}
