package com.example.projectforheadsandhands.mapper;

import com.example.projectforheadsandhands.model.dto.BattleResultDto;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;

import java.util.List;

public class BattleResultMapper {

    public static BattleResultDto buildBattleResultDto(String battleResult,
                                                       List<String> battleProgress,
                                                       ShortHeroDto shortHeroDto) {
        return BattleResultDto.builder()
                .result(battleResult)
                .battleProgress(battleProgress)
                .heroDto(shortHeroDto)
                .build();
    }
}
