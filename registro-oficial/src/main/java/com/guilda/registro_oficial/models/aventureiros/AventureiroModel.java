package com.guilda.registro_oficial.models.aventureiros;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.audit.UsuarioModel;
import com.guilda.registro_oficial.models.aventureiros.enums.ClasseEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "aventureiro", schema = "operacoes")
@Getter
@Setter
@NoArgsConstructor
public class AventureiroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacaoModel organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private UsuarioModel usuarioCadastro;

    @Column(nullable = false, length = 120)
    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @NotNull(message = "classe é obrigatória")
    private ClasseEnum classe;

    @Column(nullable = false)
    @Min(value = 1, message = "nivel deve ser maior ou igual a 1")
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CompanheiroModel companheiro;
}
