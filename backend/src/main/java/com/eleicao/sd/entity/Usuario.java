package com.eleicao.sd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // permitir espacos? caracteres especiais??
    private String nick;

    @Column(nullable = false)
    private String senha; // hash da senha
}
