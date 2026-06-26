package com.guilda.registro_oficial.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RANKING-SERVICE", fallback = RankingClientFallback.class)
public interface RankingClient {

    @PostMapping("/ranking/pontuar")
    RankingResponse pontuar(
            @RequestParam("aventureiroId") Long aventureiroId,
            @RequestParam("pontos") Integer pontos
    );
}