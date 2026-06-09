package com.eleicao.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String object;

    private Long valor;

    private LocalDateTime datetime;

    @Override
    public String toString() {
        return "Voto{" +
                ", type='" + type + '\'' +
                ", object='" + object + '\'' +
                ", valor=" + valor +
                ", datetime=" + datetime +
                '}';
    }
}
