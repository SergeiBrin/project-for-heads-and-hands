package com.example.projectforheadsandhands.service.statistics;

import com.example.projectforheadsandhands.model.BattleStatistics;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.model.dto.BattleStatisticsDto;

public interface Statistics {
    BattleStatisticsDto getStatistics(Long heroId);
    BattleStatistics addStatistics(Hero updateHero, Monster updateMonster, Boolean result);
}
