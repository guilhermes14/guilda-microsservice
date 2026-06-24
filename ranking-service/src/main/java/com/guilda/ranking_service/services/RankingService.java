package com.guilda.ranking_service.services;

import com.guilda.ranking_service.client.AventureiroResponse;
import com.guilda.ranking_service.client.GuildaClient;
import com.guilda.ranking_service.client.dto.RankingAventureiroDTO;
import com.guilda.ranking_service.models.RankingEntryModel;
import com.guilda.ranking_service.repositories.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final GuildaClient guildaClient;

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

    public List<RankingAventureiroDTO> listarRankingCompleto() {
        List<RankingEntryModel> entries = rankingRepository.findAllByOrderByPontosDesc();
        List<RankingAventureiroDTO> resultado = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++) {
            RankingEntryModel entry = entries.get(i);
            AventureiroResponse aventureiro = guildaClient.buscarAventureiro(entry.getAventureiroId());

            RankingAventureiroDTO dto = new RankingAventureiroDTO();
            dto.setPosicao(i + 1);
            dto.setAventureiroId(entry.getAventureiroId());
            dto.setNomeAventureiro(aventureiro.getNome());
            dto.setClasse(aventureiro.getClasse());
            dto.setNivel(aventureiro.getNivel());
            dto.setPontos(entry.getPontos());
            dto.setMissoesConcluidas(entry.getMissoesConcluidas());

            resultado.add(dto);
        }

        return resultado;
    }

}
