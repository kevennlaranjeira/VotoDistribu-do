package com.eleicao.core.component;

import com.eleicao.core.configuration.RabbitConfig;
import com.eleicao.core.dto.MensagemDTO;
import com.eleicao.core.service.MensagemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MensagemListener {
    private static final Logger logger = LogManager.getLogger(MensagemListener.class);

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private CandidatoPublisher candidatoPublisher;

    @Autowired
    private CidadePublisher cidadePublisher;

    @RabbitListener(queues = RabbitConfig.FILA_VOTOS)
    public void processarVoto(@Payload MensagemDTO mensagemDTO) {
        try {
            mensagemService.processarVoto(mensagemDTO);
            candidatoPublisher.publicarListaCandidatos();
            logger.info("Voto processado e lista atualizada: {}", mensagemDTO.toString());
        } catch (Exception e) {
            logger.error("Erro ao processar voto: {}", mensagemDTO.toString(), e);
            throw e;
        }
    }

    @RabbitListener(queues = RabbitConfig.FILA_CIDADES)
    public void processarAtualizacaoQualidadeAr(@Payload MensagemDTO mensagemDTO) {
        try {
            mensagemService.processarQualidadeAr(mensagemDTO);
            cidadePublisher.publicarCidades();
            logger.info("Cidade processada e lista atualizada: {}", mensagemDTO.toString());
        } catch (Exception e) {
            logger.error("Erro ao processar cidade: {}", mensagemDTO.toString(), e);
            throw e;
        }
    }

}
