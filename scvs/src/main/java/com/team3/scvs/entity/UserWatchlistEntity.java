package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_watchlist") // DB 테이블 이름 user_watchlist
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWatchlistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userWatchlistId; // user_watchlist_id와 같습니다.

    private Long userId; // users 테이블의 user_id와 연결
}
