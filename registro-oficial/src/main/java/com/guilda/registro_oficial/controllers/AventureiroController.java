package com.guilda.registro_oficial.controllers;

import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.models.aventureiros.CompanheiroModel;
import com.guilda.registro_oficial.services.AventureiroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<List<AventureiroModel>> listar(
            @RequestParam(required = false) String classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AventureiroModel> resultado = aventureiroService.listar(classe, ativo, nivelMinimo, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(resultado.getTotalElements()));
        headers.add("X-Page", String.valueOf(resultado.getNumber()));
        headers.add("X-Size", String.valueOf(resultado.getSize()));
        headers.add("X-Total-Pages", String.valueOf(resultado.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(resultado.getContent());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<AventureiroModel>> listarTodos() {
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

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroModel> definirCompanheiro(@PathVariable Long id,
                                                          @RequestBody @Valid CompanheiroModel companheiro) {
        try {
            return ResponseEntity.ok(aventureiroService.definirCompanheiro(id, companheiro));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        try {
            aventureiroService.removerCompanheiro(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
