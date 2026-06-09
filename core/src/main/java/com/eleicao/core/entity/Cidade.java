package com.eleicao.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long valor_qualidade_ar;

    private BigDecimal media;

    private Long mediana;

    @Lob
    private byte[] foto;

    public Cidade(String nome, Long valor_qualidade_ar, BigDecimal media, Long mediana, byte[] foto) {
        this.nome = nome;
        this.valor_qualidade_ar = valor_qualidade_ar;
        this.media = media;
        this.mediana = mediana;
        this.foto = foto;
    }
}
