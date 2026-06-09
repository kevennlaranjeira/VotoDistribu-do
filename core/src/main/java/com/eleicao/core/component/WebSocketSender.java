package com.eleicao.core.component;

import com.eleicao.core.entity.Candidato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSocketSender {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarCandidatos(List<Candidato> candidatos) {
        messagingTemplate.convertAndSend("/topic/candidatos", candidatos);
    }

    //funcao para expor as cidades
}
