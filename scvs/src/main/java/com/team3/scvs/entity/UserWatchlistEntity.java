package com.team3.scvs.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_watchlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWatchlistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userWatchlistId;

    private Long userId;

    @OneToMany(mappedBy = "userWatchlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchlistStocksEntity> stocks;
}
