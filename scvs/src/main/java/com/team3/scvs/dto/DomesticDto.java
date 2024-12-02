package com.team3.scvs.dto;

import com.team3.scvs.entity.DomesticEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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

    //엔티티를 dto로 변환
    public static DomesticDto toDomesticDto (DomesticEntity domesticEntity) {
        DomesticDto domesticDto = new DomesticDto();

        domesticDto.setKorEconNewsId(domesticEntity.getKorEconNewsId());
        domesticDto.setTitle(domesticEntity.getTitle());
        domesticDto.setSource(domesticEntity.getSource());
        domesticDto.setImageLink(domesticEntity.getImageLink());
        domesticDto.setContent(domesticEntity.getContent());
        domesticDto.setPublishedAt(domesticEntity.getPublishedAt());
        domesticDto.setLink(domesticEntity.getLink());

        return domesticDto;

    }

}
