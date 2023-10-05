package com.example.projectforheadsandhands.mapper;

import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;

public class HeroMapper {

    public static Hero buildHero(HeroNameDto heroNameDto,
                                 int attack,
                                 int defence,
                                 int health,
                                 int lowDamage,
                                 int highDamage,
                                 int recovery) {

        return Hero.builder()
                .name(heroNameDto.getName())
                .attack(attack)
                .defense(defence)
                .health(health)
                .originalHealth(health)
                .lowDamage(lowDamage)
                .highDamage(highDamage)
                .recovery(recovery)
                .build();
    }

    public static ShortHeroDto buildShortHeroDto(Hero hero) {
        return ShortHeroDto.builder()
                .id(hero.getId())
                .name(hero.getName())
                .health(hero.getHealth())
                .recovery(hero.getRecovery())
                .build();
    }
}
