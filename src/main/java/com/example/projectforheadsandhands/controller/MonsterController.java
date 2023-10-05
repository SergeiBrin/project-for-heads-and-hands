package com.example.projectforheadsandhands.controller;

import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.service.monster.MonsterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
@Slf4j
public class MonsterController {
    private final MonsterService monsterService;

    @GetMapping("/{monsterId}")
    public ResponseEntity<Monster> findMonsterById(@PathVariable Long monsterId) {
        log.info("Поступил GET запрос в MonsterController, метод findMonsterById(Long monsterId). " +
                "monsterId={}", monsterId);
        Monster getMonster = monsterService.findMonsterById(monsterId);
        return new ResponseEntity<>(getMonster, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Monster>> findMonsters(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("Поступил GET запрос в MonsterController, метод findMonsters(Integer pageNumber, Integer pageSize). " +
                "pageNumber={}, pageSize={}", pageNumber, pageSize);
        List<Monster> findMonsters = monsterService.findMonsters(pageNumber, pageSize);

        return new ResponseEntity<>(findMonsters, HttpStatus.OK);
    }
}
