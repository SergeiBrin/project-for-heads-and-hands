package com.example.projectforheadsandhands.service.play;

import com.example.projectforheadsandhands.model.dto.BattleResultDto;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;

import java.util.List;

public interface PlayService {
    List<Object> createNewGame(String type, String comp, Integer count, HeroNameDto heroDto);
    BattleResultDto createBattle(Long heroId, Long monsterId);

}
