package com.example.projectforheadsandhands.service.hero;

import com.example.projectforheadsandhands.exception.InvalidParamException;
import com.example.projectforheadsandhands.exception.InvalidRecoveryException;
import com.example.projectforheadsandhands.exception.NotFoundException;
import com.example.projectforheadsandhands.mapper.HeroMapper;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.dto.HeroNameDto;
import com.example.projectforheadsandhands.model.dto.ShortHeroDto;
import com.example.projectforheadsandhands.repository.HeroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class HeroServiceImpl implements HeroService {
    private final HeroRepository heroRepository;
    private final Random random = new Random();

    @Transactional(readOnly = true)
    @Override
    public Hero findHeroById(Long heroId) {
        Hero findHero = heroRepository.findById(heroId)
                .orElseThrow(() -> new NotFoundException(String.format("Героя с id=%d не существует", heroId)));
        log.info("Запрос в метод findHeroById(Long heroId) обработан успешно. " +
                 "findHero={}", findHero);

        return findHero;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Hero> findHeroes(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Hero> findHeroes = heroRepository.findAll(pageable).getContent();
        log.info("Запрос в метод findHeroes(Integer pageNumber, Integer pageSize) обработан успешно. " +
                 "findHeroes={}", findHeroes);

        return findHeroes;
    }

    @Transactional
    @Override
    public Hero createHero(String type, String comp, HeroNameDto heroDto) {
        int attack;
        int defence;
        int health;
        int lowDamage;
        int highDamage;

        if (type != null) {
            String checkType = type.toLowerCase();
            switch (checkType) {
                case "трус":
                    attack = random.nextInt(1, 11);
                    defence = random.nextInt(20, 31);
                    health = 100;
                    lowDamage = random.nextInt(1, 3);
                    highDamage = random.nextInt(lowDamage + 1, 5);
                    break;
                case "балбес":
                    attack = random.nextInt(10, 21);
                    defence = random.nextInt(10, 21);
                    health = 100;
                    lowDamage = random.nextInt(2, 4);
                    highDamage = random.nextInt(lowDamage + 1, 6);
                    break;
                case "бывалый":
                    attack = random.nextInt(20, 31);
                    defence = random.nextInt(1, 11);
                    health = 100;
                    lowDamage = random.nextInt(4, 6);
                    highDamage = random.nextInt(lowDamage + 1, 7);
                    break;
                default:
                    throw new InvalidParamException(String.format("Такой тип героя type=%s выбрать нельзя", type));
            }
        } else {
            attack = random.nextInt(1, 31);
            defence = random.nextInt(1, 31);
            health = random.nextInt(0, 101);
            lowDamage = random.nextInt(1, 6);
            highDamage = random.nextInt(lowDamage, 7);
        }

        Hero buildHero = HeroMapper.buildHero(heroDto, attack, defence, health, lowDamage, highDamage, 4);
        Hero createHero = heroRepository.save(buildHero);
        log.info("Запрос в метод createHero(String type, String comp, HeroNameDto heroDto) " +
                 "обработан успешно и добавлен в базу данных. " +
                 "createHero={}", createHero);

        return createHero;
    }

    @Transactional
    @Override
    public ShortHeroDto recoveryHero(Long heroId) {
        Hero hero = findHeroById(heroId);

        int recovery = getRecovery(heroId, hero);

        hero.setHealth(recovery);
        hero.setRecovery(hero.getRecovery() - 1);
        Hero updateHero = heroRepository.save(hero);

        log.info("Запрос в метод recoveryHero(Long heroId) " +
                 "обработан успешно и добавлен в базу данных. " +
                 "updateHero={}", updateHero);

        return HeroMapper.buildShortHeroDto(updateHero);
    }

    @Transactional
    @Override
    public Hero putHero(Hero hero) {
        Hero updateHero = heroRepository.save(hero);
        log.info("Запрос в метод putHero(Hero hero) " +
                 "обработан успешно и данные добавлены в базу данных. " +
                 "updateHero={}", updateHero);

        return updateHero;
    }

    @Transactional
    @Override
    public void deleteAll() {
        heroRepository.deleteAll();
        log.info("Запрос в метод deleteAll() обработан успешно.");
    }

    private int getRecovery(Long heroId, Hero hero) {
        if (hero.getHealth() > 0) {
            throw new InvalidRecoveryException(String.format("Герой с id=%d жив. Исцелить можно только мёртвого героя", heroId));
        }
        if (hero.getRecovery() == 0) {
            throw new InvalidRecoveryException(String.format("Герой с id=%d исчерпал доступное количество исцелений " +
                    "и отправился на небеса", heroId));
        }

        int originalHealth = hero.getOriginalHealth();

        // Округление к большему числу.
        return (int) Math.ceil(originalHealth * 0.3);
    }
}

