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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<AventureiroResponse> aventureiros = guildaClient.listarTodos();

        List<RankingEntryModel> entries = rankingRepository.findAll();
        Map<Long, RankingEntryModel> rankingMap = entries.stream()
                .collect(Collectors.toMap(RankingEntryModel::getAventureiroId, e -> e));

        // Monta a lista completa
        List<RankingAventureiroDTO> resultado = new ArrayList<>();
        for (AventureiroResponse aventureiro : aventureiros) {
            RankingEntryModel entry = rankingMap.get(aventureiro.getId());
            RankingAventureiroDTO dto = new RankingAventureiroDTO();
            dto.setAventureiroId(aventureiro.getId());
            dto.setNomeAventureiro(aventureiro.getNome());
            dto.setClasse(aventureiro.getClasse());
            dto.setNivel(aventureiro.getNivel());
            dto.setPontos(entry != null ? entry.getPontos() : 0);
            dto.setMissoesConcluidas(entry != null ? entry.getMissoesConcluidas() : 0);
            resultado.add(dto);
        }

        resultado.sort((a, b) -> b.getPontos() - a.getPontos());
        for (int i = 0; i < resultado.size(); i++) {
            resultado.get(i).setPosicao(i + 1);
        }

        return resultado;
    }

}
