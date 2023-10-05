package com.example.projectforheadsandhands.controller;

import com.example.projectforheadsandhands.model.dto.BattleResultDto;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;
import com.example.projectforheadsandhands.service.play.PlayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/play")
@RequiredArgsConstructor
@Slf4j
public class PlayController {
    private final PlayService playService;

    @PostMapping("/new-game")
    public ResponseEntity<List<Object>> createNewGame(@RequestParam(required = false) String type,
                                                      @RequestParam(required = false) String comp,
                                                      @RequestParam(defaultValue = "2") Integer count,
                                                      @Valid @RequestBody HeroNameDto heroDto) {
        log.info("Поступил POST запрос в PlayController, " +
                 "метод createNewGame(String type, String comp, Integer count, HeroNameDto heroDto). " +
                 "type={}, comp={}, count={}, heroDto={}", type, comp, count, heroDto);
        List<Object> beings = playService.createNewGame(type, comp, count, heroDto);

        return new ResponseEntity<>(beings, HttpStatus.OK);
    }

    @PostMapping("/battle/{heroId}/{monsterId}")
    public ResponseEntity<BattleResultDto> createBattle(@PathVariable Long heroId,
                                                        @PathVariable Long monsterId) {
        log.info("Поступил POST запрос в PlayController, " +
                "метод createBattle(Long heroId, Long monsterId). " +
                "heroId={}, monsterId={}", heroId, monsterId);
        BattleResultDto battleResult = playService.createBattle(heroId, monsterId);

        return new ResponseEntity<>(battleResult, HttpStatus.OK);
    }
}
