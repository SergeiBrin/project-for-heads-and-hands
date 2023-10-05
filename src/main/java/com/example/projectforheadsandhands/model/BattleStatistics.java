package com.example.projectforheadsandhands.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "battle_statistics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BattleStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero")
    private Hero hero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster")
    private Monster monster;

    private Boolean result;
}
