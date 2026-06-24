package com.guilda.ranking_service.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingAventureiroDTO {
    private Integer posicao;
    private Long aventureiroId;
    private String nomeAventureiro;
    private String classe;
    private Integer nivel;
    private Integer pontos;
    private Integer missoesConcluidas;
}
