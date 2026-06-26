package com.guilda.registro_oficial.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErroResponse {
    private String mensagem;
    private List<String> detalhes;
}
