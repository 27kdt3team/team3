package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kor_econ_news")
@Getter
@NoArgsConstructor
public class DomesticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long korEconNewsId;

    private String title;

    private String source;

    private String publishedAt;

    private String imageLink;

    public DomesticEntity(String title, String source, String publishedAt, String imageLink) {
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.imageLink = imageLink;
    }
}
