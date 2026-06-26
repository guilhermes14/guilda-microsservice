package com.guilda.registro_oficial.models.aventureiros;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guilda.registro_oficial.models.aventureiros.enums.EspecieEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "aventureiro_id")
    private AventureiroModel aventureiro;

    @Column(nullable = false, length = 120)
    @NotBlank(message = "nome do companheiro é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    @NotNull(message = "especie é obrigatória")
    private EspecieEnum especie;

    @Column(name = "indice_lealdade", nullable = false)
    @Min(value = 0, message = "lealdade deve ser entre 0 e 100")
    @Max(value = 100, message = "lealdade deve ser entre 0 e 100")
    private Integer indiceLealdade;
}
