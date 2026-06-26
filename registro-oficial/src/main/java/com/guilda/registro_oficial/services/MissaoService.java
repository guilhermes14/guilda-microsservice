package com.guilda.registro_oficial.services;

import com.guilda.registro_oficial.client.RankingClient;
import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.models.missoes.MissaoModel;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoId;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoModel;
import com.guilda.registro_oficial.models.missoes.enums.PerigoEnum;
import com.guilda.registro_oficial.models.missoes.enums.StatusMissaoEnum;
import com.guilda.registro_oficial.repositories.AventureiroRepository;
import com.guilda.registro_oficial.repositories.MissaoRepository;
import com.guilda.registro_oficial.repositories.ParticipacaoMissaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        missao.setDataCriacao(LocalDateTime.now());
        missao.setStatus(StatusMissaoEnum.PLANEJADA);
        return missaoRepository.save(missao);
    }

    public List<MissaoModel> listarTodas() {

        return missaoRepository.findAll();
    }

    public Optional<MissaoModel> buscarPorId(Long id) {
        return missaoRepository.findById(id);
    }
    
    @Transactional
    public ParticipacaoMissaoModel adicionarParticipante(Long missaoId, Long aventureiroId, String papel) {
        MissaoModel missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));
        AventureiroModel aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado"));

        List<String> papeisValidos = List.of("LIDER", "TANQUE", "ATACANTE", "SUPORTE", "EXPLORADOR", "CURANDEIRO");
        if (!papeisValidos.contains(papel.toUpperCase())) {
            throw new RuntimeException("Papel inválido. Valores aceitos: " + papeisValidos);
        }

        if (!aventureiro.getAtivo()) {
            throw new RuntimeException("Aventureiro inativo não pode participar de missões");
        }

        if (missao.getStatus() != StatusMissaoEnum.PLANEJADA &&
                missao.getStatus() != StatusMissaoEnum.EM_ANDAMENTO) {
            throw new RuntimeException("Missão não está em estado compatível para aceitar participantes");
        }

        ParticipacaoMissaoId id = new ParticipacaoMissaoId(missaoId, aventureiroId);
        ParticipacaoMissaoModel participacao = new ParticipacaoMissaoModel();
        participacao.setId(id);
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(papel.toUpperCase());
        participacao.setDestaque(false);
        participacao.setDataRegistro(LocalDateTime.now());

        ParticipacaoMissaoModel salva = participacaoRepository.save(participacao);

        int pontos = switch (missao.getNivelPerigo()) {
            case BAIXO -> 10;
            case MEDIO -> 25;
            case ALTO -> 50;
            case EXTREMO -> 100;
        };

        rankingClient.pontuar(aventureiroId, pontos);
        return salva;
    }

    private int calcularPontos(PerigoEnum nivelPerigo, Boolean destaque, BigDecimal recompensaOuro) {
        int pontos = switch (nivelPerigo) {
            case BAIXO -> 10;
            case MEDIO -> 25;
            case ALTO -> 50;
            case EXTREMO -> 100;
        };

        if (Boolean.TRUE.equals(destaque)) {
            pontos *= 2;
        }

        if (recompensaOuro != null) {
            pontos += recompensaOuro.intValue() / 10;
        }

        return pontos;
    }

    public MissaoModel atualizar(Long id, MissaoModel dados) {
        MissaoModel missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));
        missao.setTitulo(dados.getTitulo());
        missao.setNivelPerigo(dados.getNivelPerigo());
        missao.setStatus(dados.getStatus());
        missao.setDataInicio(dados.getDataInicio());
        missao.setDataFim(dados.getDataFim());
        return missaoRepository.save(missao);
    }

    public void deletar(Long id) {
        missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));
        missaoRepository.deleteById(id);
    }

}
