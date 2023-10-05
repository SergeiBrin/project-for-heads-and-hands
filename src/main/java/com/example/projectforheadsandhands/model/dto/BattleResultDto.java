package com.example.projectforheadsandhands.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class BattleResultDto {
    private String result;
    private List<String> battleProgress;
    private ShortHeroDto heroDto;
}
