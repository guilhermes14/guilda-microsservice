package com.guilda.ranking_service.client;

import org.springframework.stereotype.Component;

@Component
public class GuildaClientFallback implements GuildaClient {

    @Override
    public AventureiroResponse buscarAventureiro(Long id) {
        AventureiroResponse fallback = new AventureiroResponse();
        fallback.setId(id);
        fallback.setNome("Aventureiro desconhecido");
        fallback.setClasse("N/A");
        return fallback;
    }
}
