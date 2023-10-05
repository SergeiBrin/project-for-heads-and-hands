package com.example.projectforheadsandhands.service.play;

import com.example.projectforheadsandhands.exception.InvalidParamException;
import com.example.projectforheadsandhands.mapper.BattleResultMapper;
import com.example.projectforheadsandhands.mapper.HeroMapper;
import com.example.projectforheadsandhands.model.Being;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.model.dto.BattleResultDto;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;
import com.example.projectforheadsandhands.service.hero.HeroService;
import com.example.projectforheadsandhands.service.monster.MonsterService;
import com.example.projectforheadsandhands.service.statistics.Statistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PlayServiceImpl implements PlayService {
    private final HeroService heroService;
    private final MonsterService monsterService;
    private final Statistics battleStatistics;
    private final Random random = new Random();

    @Transactional
    @Override
    public List<Object> createNewGame(String type, String comp, Integer count, HeroNameDto heroDto) {
        if (count < 1) {
            throw new InvalidParamException("Параметр count должен быть положительным числом");
        }

        // Если есть данные в таблицах, удалить их.
        heroService.deleteAll();
        monsterService.deleteAll();

        Hero createHero = heroService.createHero(type, comp, heroDto);
        List<Monster> createMonsters = monsterService.createNewMonsters(createHero, comp, count);

        List<Object> beings = new ArrayList<>();
        beings.add(createHero);
        beings.add(createMonsters);

        return beings;
    }

    @Transactional
    @Override
    public BattleResultDto createBattle(Long heroId, Long monsterId) {
        Hero hero = heroService.findHeroById(heroId);
        if (hero.getHealth() == 0) {
            throw new InvalidParamException(String.format("Герой с id=%d мёртв", heroId));
        }

        Monster monster = monsterService.findMonsterById(monsterId);
        if (monster.getHealth() == 0) {
            throw new InvalidParamException(String.format("Монстр с id=%d мёртв", monsterId));
        }

        BattleResultDto resultDto;

        // Кидаем кубик, чтобы выбрать, кто ходит первым.
        int x = random.nextInt(2);
        if (x == 0) {
            resultDto = startBattle(hero, monster);
        } else {
            resultDto = startBattle(monster, hero);
        }

        return resultDto;
    }

    private BattleResultDto startBattle(Being op1, Being op2) {
        // Высчитываем модификатор удара для героя и для монстра
        int op1AttackMod = 1;
        int op2AttackMod = 1;

        int attackModifier = op1.getAttack() - op2.getDefense() + 1;
        if (attackModifier > 1) {
            op1AttackMod = attackModifier;
        }

        attackModifier = op2.getAttack() - op1.getDefense() + 1;
        if (attackModifier > 1) {
            op2AttackMod = attackModifier;
        }

        List<String> battleProgress = new ArrayList<>();
        String battleResult;

        battleProgress.add(String.format("Битва начинается: op1=%s vs op2=%s",
                op1.getName(), op2.getName()));

        while (true) {
            for (int i = 0; i < op1AttackMod; i++) {
                int n = random.nextInt(1, 7);

                if (n == 5 || n == 6) {
                    int damage = random.nextInt(op1.getLowDamage(), op1.getHighDamage() + 1);
                    int health = op2.getHealth();
                    op2.setHealth(health - damage);
                    battleProgress.add(String.format(
                            "op1=%s атаковал op2=%s. Урон %d. Здоровье op2=%d ",
                            op1.getName(), op2.getName(), damage, op2.getHealth()));
                    break;
                }
            }

            if (op2.getHealth() <= 0) {
                op2.setHealth(0);
                battleResult = "битва закончена. Победил " + op1.getClass().getSimpleName();
                break;
            }

            for (int i = 0; i < op2AttackMod; i++) {
                int n = random.nextInt(1, 7);

                if (n == 5 || n == 6) {
                    int damage = random.nextInt(op2.getLowDamage(), op2.getHighDamage() + 1);
                    int health = op1.getHealth();
                    op1.setHealth(health - damage);
                    battleProgress.add(String.format(
                            "op2=%s атаковал op1=%s. Урон %d. Здоровье op1=%d ",
                            op2.getName(), op1.getName(), damage, op1.getHealth()));
                    break;
                }
            }

            if (op1.getHealth() <= 0) {
                op1.setHealth(0);
                battleResult = "битва закончена. Победил " + op2.getClass().getSimpleName();
                break;
            }
        }

        Hero hero = new Hero();
        Monster monster = new Monster();

        if (op1 instanceof Hero) {
            hero = (Hero) op1;
            monster = (Monster) op2;
        }
        if (op1 instanceof Monster) {
            hero = (Hero) op2;
            monster = (Monster) op1;
        }

        Hero updateHero = heroService.putHero(hero);
        Monster updateMonster = monsterService.putMonster(monster);

        ShortHeroDto shortHeroDto = HeroMapper.buildShortHeroDto(updateHero);

        if (shortHeroDto.getHealth() != 0) {
            battleStatistics.addStatistics(updateHero, updateMonster, true);
        } else {
            battleStatistics.addStatistics(updateHero, updateMonster, false);
        }

        return BattleResultMapper.buildBattleResultDto(battleResult, battleProgress, shortHeroDto);
    }
}
