package com.example.projectforheadsandhands.service.monster;

import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;

import java.util.List;

public interface MonsterService {
    Monster findMonsterById(Long monsterId);
    List<Monster> findMonsters(Integer pageNumber, Integer pageSize);
    List<Monster> createNewMonsters(Hero hero, String comp, Integer count);
    Monster putMonster(Monster monster);
    void deleteAll();
}
