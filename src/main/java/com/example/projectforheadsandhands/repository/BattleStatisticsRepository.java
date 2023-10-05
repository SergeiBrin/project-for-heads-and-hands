package com.example.projectforheadsandhands.repository;

import com.example.projectforheadsandhands.model.BattleStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleStatisticsRepository extends JpaRepository<BattleStatistics, Long> {
    @Query("select bs " +
            "from BattleStatistics bs " +
            "join fetch bs.hero h " +
            "where h.id = :heroId")
    Page<BattleStatistics> findByHeroId(Long heroId, Pageable pageable);

    Long countByHeroIdAndResult(Long heroId, Boolean result);
}
