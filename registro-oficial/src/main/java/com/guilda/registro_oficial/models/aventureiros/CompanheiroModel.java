package com.guilda.registro_oficial.models.aventureiros;

import com.guilda.registro_oficial.models.aventureiros.enums.EspecieEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companheiro", schema = "operacoes")
@Getter
@Setter
@NoArgsConstructor
public class CompanheiroModel {
    @Id
    @Column(name = "aventureiro_id")
    private Long aventureiroId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "aventureiro_id")
    private AventureiroModel aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private EspecieEnum especie;

    @Column(name = "indice_lealdade", nullable = false)
    private Integer indiceLealdade;
}
