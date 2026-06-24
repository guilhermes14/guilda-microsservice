package com.guilda.ranking_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ranking_entry")
@Getter
@Setter
@NoArgsConstructor
public class RankingEntryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aventureiro_id", nullable = false, unique = true)
    private Long aventureiroId;

    @Column(nullable = false)
    private Integer pontos = 0;

    @Column(name = "missoes_concluidas", nullable = false)
    private Integer missoesConcluidas = 0;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
}
