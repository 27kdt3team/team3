package com.team3.scvs.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsaDto {
    private Long usaEconNewsId;
    private String title;
    private String source;
    private String imageLink;
    private String content;
    private LocalDateTime publishedAt;
    private String link;

    public UsaDto(Long usaEconNewsId, String title, String source, LocalDateTime publishedAt, String imageLink) {
        this.usaEconNewsId = usaEconNewsId;
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;

    }

}
