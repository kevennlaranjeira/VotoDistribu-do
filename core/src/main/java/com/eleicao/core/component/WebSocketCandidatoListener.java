package com.eleicao.core.component;


import com.eleicao.core.configuration.RabbitConfig;
import com.eleicao.core.entity.Candidato;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WebSocketCandidatoListener {
    private static final Logger logger = LogManager.getLogger(WebSocketCandidatoListener.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketCandidatoListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitConfig.FILA_WEBSOCKET_CANDIDATOS)
    public void enviarCandidatosViaWebSocket(@Payload List<Candidato> candidatos) {
        try {
            logger.info("Recebido lista de candidatos via RabbitMQ, enviando via WebSocket: {} candidatos",
                    candidatos.size());
            messagingTemplate.convertAndSend("/topic/candidatos", candidatos);
            logger.info("Lista de candidatos enviada via WebSocket com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao enviar candidatos via WebSocket", e);
        }
    }
}
