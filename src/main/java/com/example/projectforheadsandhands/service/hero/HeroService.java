package com.example.projectforheadsandhands.service.hero;

import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;

import java.util.List;

public interface HeroService {
    Hero findHeroById(Long heroId);
    List<Hero> findHeroes(Integer pageNumber, Integer pageSize);
    Hero createHero(String type, String comp, HeroNameDto heroDto);
    ShortHeroDto recoveryHero(Long heroId);
    Hero putHero(Hero hero);
    void deleteAll();
}
