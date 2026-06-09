package com.eleicao.core.component;

import com.eleicao.core.dto.CandidatoDTO;
import com.eleicao.core.service.CandidatoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CandidatoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final CandidatoService candidatoService;

    public CandidatoPublisher(RabbitTemplate rabbitTemplate, CandidatoService candidatoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.candidatoService = candidatoService;
    }

    public void publicarListaCandidatos() {
        List<CandidatoDTO> candidatos = candidatoService.listarPorQuantidadeVotosDesc();
        rabbitTemplate.convertAndSend("exchange-candidatos", "", candidatos);
    }
}
