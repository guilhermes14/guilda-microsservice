package com.guilda.registro_oficial.controllers;

import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.services.AventureiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventureiroService aventureiroService;

    @PostMapping
    public ResponseEntity<AventureiroModel> criar(@RequestBody @Valid AventureiroModel aventureiro) {
        AventureiroModel criado = aventureiroService.criar(aventureiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroModel>> listar() {
        return ResponseEntity.ok(aventureiroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroModel> buscar(@PathVariable Long id) {
        return aventureiroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroModel> atualizar(@PathVariable Long id,
                                                 @RequestBody @Valid AventureiroModel dados) {
        try {
            return ResponseEntity.ok(aventureiroService.atualizar(id, dados));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        try {
            aventureiroService.encerrarVinculo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id) {
        try {
            aventureiroService.recrutar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
