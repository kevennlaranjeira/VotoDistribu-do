package com.eleicao.core.component;

import com.eleicao.core.dto.CidadeDTO;
import com.eleicao.core.service.CidadeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final CidadeService cidadeService;

    public CidadePublisher(RabbitTemplate rabbitTemplate, CidadeService cidadeService){
        this.rabbitTemplate = rabbitTemplate;
        this.cidadeService = cidadeService;

    }

    public void publicarCidades() {
        List<CidadeDTO> cidades = cidadeService.listAll();
        rabbitTemplate.convertAndSend("websocket-cidades-fila", "", cidades);
    }
}
