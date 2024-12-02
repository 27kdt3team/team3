package com.team3.scvs.dto;

import com.team3.scvs.entity.UsaEntity;
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

    //뉴스 리스트 생성자
    public UsaDto(Long usaEconNewsId, String title, String source, LocalDateTime publishedAt, String imageLink) {
        this.usaEconNewsId = usaEconNewsId;
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;

    }

    //엔티티를 dto로 변환
    public static UsaDto toUsaDto(UsaEntity usaEntity) {
        UsaDto usaDto = new UsaDto();

        usaDto.setUsaEconNewsId(usaEntity.getUsaEconNewsId());
        usaDto.setTitle(usaEntity.getTitle());
        usaDto.setSource(usaEntity.getSource());
        usaDto.setImageLink(usaEntity.getImageLink());
        usaDto.setContent(usaEntity.getContent());
        usaDto.setPublishedAt(usaEntity.getPublishedAt());
        usaDto.setLink(usaEntity.getLink());

        return usaDto;

    }

}
