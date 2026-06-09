package com.eleicao.sd.component;

import com.eleicao.sd.configuration.RabbitConfig;
import com.eleicao.sd.dto.VotoDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotoSender {

    private final AmqpTemplate amqpTemplate;

    public VotoSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private static final Logger logger = LogManager.getLogger(VotoSender.class);

    public void enviarVoto(VotoDTO voto) {
        amqpTemplate.convertAndSend(RabbitConfig.FILA_VOTOS, voto);
        logger.info("Voto enviado: {}", voto.toString());
    }
}
