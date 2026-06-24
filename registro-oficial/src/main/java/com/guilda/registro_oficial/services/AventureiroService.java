package com.guilda.registro_oficial.services;

import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.repositories.AventureiroRepository;
import com.guilda.registro_oficial.repositories.OrganizacaoRepository;
import com.guilda.registro_oficial.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AventureiroModel criar(AventureiroModel aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }

    public List<AventureiroModel> listarTodos() {
        return aventureiroRepository.findAll();
    }

    public Optional<AventureiroModel> buscarPorId(Long id) {
        return aventureiroRepository.findById(id);
    }

    public AventureiroModel atualizar(Long id, AventureiroModel dados) {
        AventureiroModel aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado"));
        aventureiro.setNome(dados.getNome());
        aventureiro.setClasse(dados.getClasse());
        aventureiro.setNivel(dados.getNivel());
        return aventureiroRepository.save(aventureiro);
    }

    public void encerrarVinculo(Long id) {
        AventureiroModel aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado"));
        aventureiro.setAtivo(false);
        aventureiroRepository.save(aventureiro);
    }

    public void recrutar(Long id) {
        AventureiroModel aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado"));
        aventureiro.setAtivo(true);
        aventureiroRepository.save(aventureiro);
    }
}
