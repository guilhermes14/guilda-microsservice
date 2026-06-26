package com.guilda.ranking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GUILDA-APP", fallback = GuildaClientFallback.class)
public interface GuildaClient {

    @GetMapping("/aventureiros/{id}")
    AventureiroResponse buscarAventureiro(@PathVariable Long id);

    @GetMapping("/aventureiros/todos")
    List<AventureiroResponse> listarTodos();
}
