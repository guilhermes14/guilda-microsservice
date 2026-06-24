package com.guilda.ranking_service.services;

import com.guilda.ranking_service.models.RankingEntryModel;
import com.guilda.ranking_service.repositories.RankingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingService rankingService;

    @Test
    void devePontuarAventureiroNovo() {
        Long aventureiroId = 1L;
        Integer pontos = 10;

        when(rankingRepository.findByAventureiroId(aventureiroId))
                .thenReturn(Optional.empty());
        when(rankingRepository.save(any(RankingEntryModel.class)))
                .thenAnswer(i -> i.getArgument(0));

        RankingEntryModel result = rankingService.pontuar(aventureiroId, pontos);

        assertThat(result.getAventureiroId()).isEqualTo(aventureiroId);
        assertThat(result.getPontos()).isEqualTo(pontos);
        assertThat(result.getMissoesConcluidas()).isEqualTo(1);
    }

    @Test
    void deveAcumularPontosAventureiroExistente() {
        Long aventureiroId = 1L;
        RankingEntryModel existente = new RankingEntryModel();
        existente.setAventureiroId(aventureiroId);
        existente.setPontos(10);
        existente.setMissoesConcluidas(1);

        when(rankingRepository.findByAventureiroId(aventureiroId))
                .thenReturn(Optional.of(existente));
        when(rankingRepository.save(any(RankingEntryModel.class)))
                .thenAnswer(i -> i.getArgument(0));

        RankingEntryModel result = rankingService.pontuar(aventureiroId, 20);

        assertThat(result.getPontos()).isEqualTo(30);
        assertThat(result.getMissoesConcluidas()).isEqualTo(2);
    }

    @Test
    void deveRetornarRankingOrdenado() {
        List<RankingEntryModel> lista = List.of(
                criarEntry(1L, 100),
                criarEntry(2L, 50)
        );

        when(rankingRepository.findAllByOrderByPontosDesc()).thenReturn(lista);

        List<RankingEntryModel> result = rankingService.listarRanking();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPontos()).isEqualTo(100);
    }

    private RankingEntryModel criarEntry(Long aventureiroId, Integer pontos) {
        RankingEntryModel entry = new RankingEntryModel();
        entry.setAventureiroId(aventureiroId);
        entry.setPontos(pontos);
        entry.setMissoesConcluidas(1);
        return entry;
    }
}
