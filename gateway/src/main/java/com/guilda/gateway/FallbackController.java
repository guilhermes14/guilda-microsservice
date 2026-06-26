package com.guilda.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback/guilda-app")
    public ResponseEntity<Map<String, String>> guildaAppFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "indisponível");
        response.put("mensagem", "O serviço Guilda App está temporariamente indisponível. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/fallback/ranking-service")
    public ResponseEntity<Map<String, String>> rankingFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "indisponível");
        response.put("mensagem", "O serviço de Ranking está temporariamente indisponível. Tente novamente mais tarde.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
