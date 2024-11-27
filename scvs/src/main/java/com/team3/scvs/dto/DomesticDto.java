package com.team3.scvs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DomesticDto {
    private String title;
    private String source;
    private String publishedAt;
    private String imageLink;

    public DomesticDto(String title, String source, String publishedAt, String imageLink) {
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;

    }

}
