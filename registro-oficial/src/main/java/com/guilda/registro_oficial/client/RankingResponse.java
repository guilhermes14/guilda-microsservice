package com.guilda.registro_oficial.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankingResponse {
    private Long id;
    private Long aventureiroId;
    private Integer pontos;
    private Integer missoesConcluidas;
}
