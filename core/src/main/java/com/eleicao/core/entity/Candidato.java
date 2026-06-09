package com.eleicao.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private Long quantidadeVotos;

    @Lob
    private byte[] foto;

    private BigDecimal porcentagem;

    public Candidato(String nome, Long quantidadeVotos, byte[] bytes, BigDecimal porcentagem) {
        this.nome = nome;
        this.quantidadeVotos = quantidadeVotos;
        this.foto = bytes;
        this.porcentagem = porcentagem;
    }


    @PrePersist
    private void onCreateQuantidadeVotos(){
        this.quantidadeVotos = 0L;
    }


}
