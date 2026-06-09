package com.eleicao.core.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {

    private String nome;

    private Long valor_qualidade_ar;

    private BigDecimal media;

    private Long mediana;
}
