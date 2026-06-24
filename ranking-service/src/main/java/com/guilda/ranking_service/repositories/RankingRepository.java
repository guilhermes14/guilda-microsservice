package com.guilda.ranking_service.repositories;

import com.guilda.ranking_service.models.RankingEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntryModel, Long> {
    Optional<RankingEntryModel> findByAventureiroId(Long aventureiroId);
    List<RankingEntryModel> findAllByOrderByPontosDesc();
}
