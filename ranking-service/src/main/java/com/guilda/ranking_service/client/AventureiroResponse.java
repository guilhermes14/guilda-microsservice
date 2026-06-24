package com.guilda.ranking_service.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AventureiroResponse {
    private Long id;
    private String nome;
    private String classe;
    private Integer nivel;
    private Boolean ativo;
}
