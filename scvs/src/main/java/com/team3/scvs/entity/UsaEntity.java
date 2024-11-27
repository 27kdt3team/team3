package com.team3.scvs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usa_econ_news")
@Getter
@NoArgsConstructor
public class UsaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usaEconNewsId;

    private String title;
    private String source;
    private String publishedAt;
    private String imageLink;

    public UsaEntity(String title, String source, String publishedAt, String imageLink) {
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;

    }

}
