package com.eleicao.core.component;

import com.eleicao.core.configuration.RabbitConfig;
import com.eleicao.core.entity.Cidade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSocketCidadeListener {

    private static final Logger logger = LogManager.getLogger(WebSocketCidadeListener.class);
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketCidadeListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitConfig.FILA_WEBSOCKET_CIDADES)
    public void enviarCidadesViaWebSocket(@Payload List<Cidade> cidades) {
        try {
            logger.info("Recebido lista de cidades via RabbitMQ, enviando via WebSocket: {} cidades", cidades.size());
            messagingTemplate.convertAndSend("/topic/cidades", cidades);
            logger.info("Lista de cidades enviada via WebSocket com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao enviar cidades via WebSocket", e);
        }
    }
}