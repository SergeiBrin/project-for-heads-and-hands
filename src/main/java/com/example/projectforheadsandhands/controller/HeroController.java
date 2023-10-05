package com.example.projectforheadsandhands.controller;

import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;
import com.example.projectforheadsandhands.service.hero.HeroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hero")
@RequiredArgsConstructor
@Slf4j
public class HeroController {
    private final HeroService heroService;

    @GetMapping("/{heroId}")
    public ResponseEntity<Hero> findHeroById(@PathVariable Long heroId) {
        log.info("Поступил GET запрос в HeroController, метод findHeroById(Long heroId). " +
                "heroId={}", heroId);
        Hero getHero = heroService.findHeroById(heroId);
        return new ResponseEntity<>(getHero, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Hero>> findHeroes(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("Поступил GET запрос в HeroController, метод findHeroes(Integer pageNumber, Integer pageSize). " +
                "pageNumber={}, pageSize={}", pageNumber, pageSize);
        List<Hero> findHeroes = heroService.findHeroes(pageNumber, pageSize);
        return new ResponseEntity<>(findHeroes, HttpStatus.OK);
    }

    // исцелить героя до 4-х раз на 30% от изначального максимального уровня здоровья
    @PatchMapping("/recovery/{heroId}")
    public ResponseEntity<ShortHeroDto> healHero(@PathVariable(required = false) Long heroId) {
        log.info("Поступил PATCH запрос в HeroController, метод healHero(Long heroId). " +
                "heroId={}", heroId);
        ShortHeroDto healedHero = heroService.recoveryHero(heroId);

        return new ResponseEntity<>(healedHero, HttpStatus.OK);
    }
}
