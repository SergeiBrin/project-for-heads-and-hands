package com.example.projectforheadsandhands.service.statistics;

import com.example.projectforheadsandhands.exception.NotFoundException;
import com.example.projectforheadsandhands.mapper.BattleStatisticsMapper;
import com.example.projectforheadsandhands.model.BattleStatistics;
import com.example.projectforheadsandhands.model.Hero;
import com.example.projectforheadsandhands.model.Monster;
import com.example.projectforheadsandhands.model.dto.BattleStatisticsDto;
import com.example.projectforheadsandhands.repository.BattleStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsImpl implements Statistics {
    private final BattleStatisticsRepository statisticsRepository;

    @Transactional(readOnly = true)
    @Override
    public BattleStatisticsDto getStatistics(Long heroId) {
        PageRequest pageable = PageRequest.of(0, 1);

        List<BattleStatistics> findStatistics = statisticsRepository.findByHeroId(heroId, pageable).getContent();
        if (findStatistics.isEmpty()) {
            throw new NotFoundException(String.format("Статистики для Hero c id=%d ещё нет", heroId));
        }

        Hero hero = findStatistics.get(0).getHero();
        Long win = statisticsRepository.countByHeroIdAndResult(heroId, true);
        Long lose = statisticsRepository.countByHeroIdAndResult(heroId, false);

        BattleStatisticsDto battleStatisticsDto = BattleStatisticsMapper.buildBattleStatisticsDto(hero, win, lose);
        log.info("Запрос в метод getStatistics(Long heroId) обработан успешно. " +
                 "battleStatistics={}", battleStatisticsDto);

        return battleStatisticsDto;
    }

    @Transactional
    @Override
    public BattleStatistics addStatistics(Hero hero, Monster monster, Boolean result) {
        BattleStatistics buildBattleStatistics = BattleStatisticsMapper.buildBattleStatistics(hero, monster, result);
        BattleStatistics addBattleStatistics = statisticsRepository.save(buildBattleStatistics);
        log.info("Запрос в метод addStatistics(Hero hero, Monster monster, Boolean result) " +
                 "обработан успешно и данные добавлены в базу данных. " +
                 "addBattleStatistics={}", addBattleStatistics);

        return addBattleStatistics;
    }
}
