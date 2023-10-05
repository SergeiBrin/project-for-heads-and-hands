package com.example.projectforheadsandhands.controller;

import com.example.projectforheadsandhands.model.dto.BattleStatisticsDto;
import com.example.projectforheadsandhands.service.statistics.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class BattleStatisticsController {
    private final Statistics battleStatistics;

    @GetMapping("/{heroId}")
    public ResponseEntity<BattleStatisticsDto> getStatisticsForHero(@PathVariable Long heroId) {
        log.info("");
        BattleStatisticsDto getStatistics = battleStatistics.getStatistics(heroId);

        return new ResponseEntity<>(getStatistics, HttpStatus.OK);
    }
}
