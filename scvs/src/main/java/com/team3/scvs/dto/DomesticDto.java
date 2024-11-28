package com.team3.scvs.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DomesticDto {
    private String title;
    private String source;
    private String publishedAt;
    private String imageLink;

    /*
    //국내 경제 뉴스 리스트 보여주기(엔티티를 dto로 변환)
    public static DomesticDto toDomesticDto(DomesticEntity domesticEntity) {
        DomesticDto domesticDto = new DomesticDto();

        domesticDto.setTitle(domesticEntity.getTitle());
        domesticDto.setSource(domesticEntity.getSource());
        domesticDto.setPublishedAt(domesticEntity.getPublishedAt());
        domesticDto.setImageLink(domesticEntity.getImageLink());

        return domesticDto;

    }
    */

}
