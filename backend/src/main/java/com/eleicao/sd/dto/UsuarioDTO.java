package com.eleicao.sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    String nick;
    private String senha;

}