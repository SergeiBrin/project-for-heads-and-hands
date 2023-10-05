package com.example.projectforheadsandhands.service.monster;

import com.example.projectforheadsandhands.exception.InvalidParamException;
import com.example.projectforheadsandhands.exception.NotFoundException;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.repository.MonsterRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonsterServiceImpl implements MonsterService {
    private final MonsterRepository monsterRepository;
    private final Random random = new Random();
    private final Faker faker = new Faker();

    @Transactional(readOnly = true)
    @Override
    public Monster findMonsterById(Long monsterId) {
        return monsterRepository.findById(monsterId)
                .orElseThrow(() -> new NotFoundException(String.format("Монстра с id=%d не существует", monsterId)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Monster> findMonsters(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Monster> findMonsters = monsterRepository.findAll(pageable).getContent();
        log.info("");

        return findMonsters;
    }

    @Transactional
    @Override
    public List<Monster> createNewMonsters(Hero hero, String comp, Integer count) {
        List<Monster> monsters;

        if (comp != null) {
            String checkComp = comp.toLowerCase();
            monsters = switch (checkComp) {
                case "easy" -> buildEasyMonsters(hero, count);
                case "medium" -> buildMediumMonsters(hero, count);
                case "hard" -> buildHardMonsters(hero, count);
                default -> throw new InvalidParamException(String.format("Такой тип сложности comp=%s выбрать нельзя", comp));
            };
        } else {
            monsters = buildRandomMonsters(count);
        }

        List<Monster> createMonsters = monsterRepository.saveAll(monsters);
        log.info("");

        return createMonsters;
    }

    @Transactional
    @Override
    public Monster putMonster(Monster monster) {
        Monster updateMonster = monsterRepository.save(monster);
        log.info("");

        return updateMonster;
    }

    @Transactional
    @Override
    public void deleteAll() {
        monsterRepository.deleteAll();
        log.info("");
    }

    private List<Monster> buildEasyMonsters(Hero hero, Integer count) {
        List<Monster> monsters = new ArrayList<>();
        int attack;
        int defence;
        int health;
        int lowDamage;
        int highDamage;

        for (int i = 0; i < count; i++) {
            attack = random.nextInt(1, hero.getAttack());
            defence = random.nextInt(1, hero.getDefense());
            health = 100;
            lowDamage = random.nextInt(1, 6);
            highDamage = random.nextInt(lowDamage + 1, 7);

            Monster monster = Monster.builder()
                    .name(faker.name().name())
                    .attack(attack)
                    .defense(defence)
                    .health(health)
                    .lowDamage(lowDamage)
                    .highDamage(highDamage)
                    .build();

            monsters.add(monster);
        }

        return monsters;
    }

    private List<Monster> buildMediumMonsters(Hero hero, Integer count) {
        List<Monster> monsters = new ArrayList<>();
        int attack;
        int defence;
        int health;
        int lowDamage;
        int highDamage;

        int combatRating = hero.getAttack() + hero.getDefense();
        int attackMin = Math.min(combatRating, 30);

        for (int i = 0; i < count; i++) {
            attack = random.nextInt(1, attackMin);
            defence = Math.min(combatRating - attack, 30);
            health = 100;
            lowDamage = random.nextInt(1, 6);
            highDamage = random.nextInt(lowDamage + 1, 7);

            Monster monster = Monster.builder()
                    .name(faker.name().name())
                    .attack(attack)
                    .defense(defence)
                    .health(health)
                    .lowDamage(lowDamage)
                    .highDamage(highDamage)
                    .build();

            monsters.add(monster);
        }

        return monsters;
    }

    private List<Monster> buildHardMonsters(Hero hero, Integer count) {
        List<Monster> monsters = new ArrayList<>();
        int attack;
        int defence;
        int health;
        int lowDamage;
        int highDamage;

        for (int i = 0; i < count; i++) {
            attack = random.nextInt(hero.getAttack(), 31);
            defence = random.nextInt(hero.getDefense(), 31);
            health = 100;
            lowDamage = random.nextInt(1, 6);
            highDamage = random.nextInt(lowDamage + 1, 7);

            Monster monster = Monster.builder()
                    .name(faker.name().name())
                    .attack(attack)
                    .defense(defence)
                    .health(health)
                    .lowDamage(lowDamage)
                    .highDamage(highDamage)
                    .build();

            monsters.add(monster);
        }

        return monsters;
    }

    private List<Monster> buildRandomMonsters(Integer count) {
        List<Monster> monsters = new ArrayList<>();
        int attack;
        int defence;
        int health;
        int lowDamage;
        int highDamage;

        for (int i = 0; i < count; i++) {
            attack = random.nextInt(1, 31);
            defence = random.nextInt(1, 31);
            health = random.nextInt(1, 101);
            lowDamage = random.nextInt(1, 6);
            highDamage = random.nextInt(lowDamage + 1, 7);

            Monster monster = Monster.builder()
                    .name(faker.name().name())
                    .attack(attack)
                    .defense(defence)
                    .health(health)
                    .lowDamage(lowDamage)
                    .highDamage(highDamage)
                    .build();

            monsters.add(monster);
        }

        return monsters;
    }
}
