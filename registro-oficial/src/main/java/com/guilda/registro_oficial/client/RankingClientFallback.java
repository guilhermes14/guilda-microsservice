package com.guilda.registro_oficial.client;

import org.springframework.stereotype.Component;

@Component
public class RankingClientFallback implements RankingClient {

    @Override
    public RankingResponse pontuar(Long aventureiroId, Integer pontos) {
        RankingResponse fallback = new RankingResponse();
        fallback.setAventureiroId(aventureiroId);
        fallback.setPontos(-1);
        fallback.setMissoesConcluidas(-1);
        return fallback;
    }
}