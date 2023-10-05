package com.example.projectforheadsandhands.model.dto;

import com.example.projectforheadsandhands.model.Hero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BattleStatisticsDto {
    private Hero hero;
    private Long win;
    private Long lose;
}
