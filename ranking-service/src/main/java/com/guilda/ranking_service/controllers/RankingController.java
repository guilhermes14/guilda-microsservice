package com.guilda.ranking_service.controllers;

import com.guilda.ranking_service.client.dto.RankingAventureiroDTO;
import com.guilda.ranking_service.models.RankingEntryModel;
import com.guilda.ranking_service.services.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/completo")
    public ResponseEntity<List<RankingAventureiroDTO>> listarCompleto() {
        return ResponseEntity.ok(rankingService.listarRankingCompleto());
    }

    @PostMapping("/pontuar")
    public ResponseEntity<RankingEntryModel> pontuar(
            @RequestParam Long aventureiroId,
            @RequestParam Integer pontos) {
        return ResponseEntity.ok(rankingService.pontuar(aventureiroId, pontos));
    }

    @GetMapping
    public ResponseEntity<List<RankingEntryModel>> listar() {
        return ResponseEntity.ok(rankingService.listarRanking());
    }

    @GetMapping("/{aventureiroId}")
    public ResponseEntity<RankingEntryModel> buscar(@PathVariable Long aventureiroId) {
        return rankingService.buscarPorAventureiro(aventureiroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
