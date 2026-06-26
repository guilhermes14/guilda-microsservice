package com.guilda.registro_oficial.models.missoes;

import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "participacao_missao", schema = "operacoes")
@Getter
@Setter
@NoArgsConstructor
public class ParticipacaoMissaoModel {
    @EmbeddedId
    private ParticipacaoMissaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("missaoId")
    @JoinColumn(name = "missao_id", nullable = false)
    private MissaoModel missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("aventureiroId")
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private AventureiroModel aventureiro;

    @Column(nullable = false, length = 40)
    private String papel;

    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro;

    @Column(nullable = false)
    private Boolean destaque;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;

}
