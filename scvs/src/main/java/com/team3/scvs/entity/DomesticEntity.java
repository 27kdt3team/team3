package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "kor_econ_news")
@Getter
@Setter
public class DomesticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long korEconNewsId;

    private String title;
    private String source;
    private String publishedAt;
    private String imageLink;

}
