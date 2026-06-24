package com.guilda.ranking_service.services;

import com.guilda.ranking_service.models.RankingEntryModel;
import com.guilda.ranking_service.repositories.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingEntryModel pontuar(Long aventureiroId, Integer pontos) {
        RankingEntryModel entry = rankingRepository.findByAventureiroId(aventureiroId)
                .orElseGet(() -> {
                    RankingEntryModel novo = new RankingEntryModel();
                    novo.setAventureiroId(aventureiroId);
                    novo.setPontos(0);
                    novo.setMissoesConcluidas(0);
                    return novo;
                });

        entry.setPontos(entry.getPontos() + pontos);
        entry.setMissoesConcluidas(entry.getMissoesConcluidas() + 1);
        entry.setUltimaAtualizacao(LocalDateTime.now());

        return rankingRepository.save(entry);
    }

    public List<RankingEntryModel> listarRanking() {
        return rankingRepository.findAllByOrderByPontosDesc();
    }

    public Optional<RankingEntryModel> buscarPorAventureiro(Long aventureiroId) {
        return rankingRepository.findByAventureiroId(aventureiroId);
    }
}
