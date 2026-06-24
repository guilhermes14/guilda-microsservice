package com.guilda.registro_oficial.controllers;

import com.guilda.registro_oficial.models.missoes.MissaoModel;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoModel;
import com.guilda.registro_oficial.services.MissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@RequiredArgsConstructor
public class MissaoController {

    private final MissaoService missaoService;

    @PostMapping
    public ResponseEntity<MissaoModel> criar(@RequestBody @Valid MissaoModel missao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(missaoService.criar(missao));
    }

    @GetMapping
    public ResponseEntity<List<MissaoModel>> listar() {
        return ResponseEntity.ok(missaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoModel> buscar(@PathVariable Long id) {
        return missaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{missaoId}/participantes/{aventureiroId}")
    public ResponseEntity<ParticipacaoMissaoModel> adicionarParticipante(
            @PathVariable Long missaoId,
            @PathVariable Long aventureiroId,
            @RequestParam String papel) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(missaoService.adicionarParticipante(missaoId, aventureiroId, papel));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
