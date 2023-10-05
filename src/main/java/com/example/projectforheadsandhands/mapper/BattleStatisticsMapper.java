package com.example.projectforheadsandhands.mapper;

import com.example.projectforheadsandhands.model.BattleStatistics;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.model.dto.BattleStatisticsDto;

public class BattleStatisticsMapper {

    public static BattleStatistics buildBattleStatistics(Hero hero, Monster monster, Boolean result) {
        return BattleStatistics.builder()
                .hero(hero)
                .monster(monster)
                .result(result)
                .build();
    }

    public static BattleStatisticsDto buildBattleStatisticsDto(Hero hero, Long win, Long lose) {
        return BattleStatisticsDto.builder()
                .hero(hero)
                .win(win)
                .lose(lose)
                .build();
    }
}
