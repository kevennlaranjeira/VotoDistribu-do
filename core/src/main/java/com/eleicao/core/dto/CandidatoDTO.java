package com.eleicao.core.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO {

    private Long id;

    private String nome;

    private Long quantidadeVotos;

    private BigDecimal porcentagem;


    @Override
    public String toString() {
        return "CandidatoDTO{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
