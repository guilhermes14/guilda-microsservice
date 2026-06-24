package com.guilda.registro_oficial.services;

import com.guilda.registro_oficial.client.RankingClient;
import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.models.missoes.MissaoModel;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoId;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoModel;
import com.guilda.registro_oficial.repositories.AventureiroRepository;
import com.guilda.registro_oficial.repositories.MissaoRepository;
import com.guilda.registro_oficial.repositories.ParticipacaoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;
    private final RankingClient rankingClient;

    public MissaoModel criar(MissaoModel missao) {
        return missaoRepository.save(missao);
    }

    public List<MissaoModel> listarTodas() {
        return missaoRepository.findAll();
    }

    public Optional<MissaoModel> buscarPorId(Long id) {
        return missaoRepository.findById(id);
    }

    public ParticipacaoMissaoModel adicionarParticipante(Long missaoId, Long aventureiroId, String papel) {
        MissaoModel missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));
        AventureiroModel aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado"));

        if (!aventureiro.getAtivo()) {
            throw new RuntimeException("Aventureiro inativo não pode participar de missões");
        }

        ParticipacaoMissaoId id = new ParticipacaoMissaoId(missaoId, aventureiroId);
        ParticipacaoMissaoModel participacao = new ParticipacaoMissaoModel() ;
        participacao.setId(id);
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(papel);
        participacao.setDestaque(false);

        ParticipacaoMissaoModel salva = participacaoRepository.save(participacao);
        rankingClient.pontuar(aventureiroId, 10);

        return salva;
    }
}
