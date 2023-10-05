package com.example.projectforheadsandhands.mapper;

import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;

public class HeroMapper {

    public static ShortHeroDto buildShortHeroDto(Hero hero) {
        return ShortHeroDto.builder()
                .id(hero.getId())
                .name(hero.getName())
                .health(hero.getHealth())
                .recovery(hero.getRecovery())
                .build();
    }
}
