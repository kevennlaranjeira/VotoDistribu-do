package com.eleicao.core.utils;

import com.eleicao.core.entity.Candidato;
import com.eleicao.core.entity.Cidade;
import com.eleicao.core.repository.CandidatoRepository;
import com.eleicao.core.repository.CidadeRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class DadosIniciais implements CommandLineRunner {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private static final Logger logger = LogManager.getLogger(DadosIniciais.class);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Criando a base de dados.");
        if (candidatoRepository.count() == 0) {
            candidatoRepository.save(new Candidato("Pedro Damaso", 0L, lerImagem("/imagens/votacao/1.png"), BigDecimal.ZERO));
            candidatoRepository.save(new Candidato("Romário", 0L, lerImagem("/imagens/votacao/2.png"), BigDecimal.ZERO));
            candidatoRepository.save(new Candidato("Tirica", 0L, lerImagem("/imagens/votacao/3.png"), BigDecimal.ZERO));
            candidatoRepository.save(new Candidato("Prefeito de Sorocaba-SP", 0L, lerImagem("/imagens/votacao/4.png"), BigDecimal.ZERO));
            candidatoRepository.save(new Candidato("Pastor Mirim", 0L, lerImagem("/imagens/votacao/5.png"), BigDecimal.ZERO));
        }

        if(cidadeRepository.count() == 0){
            cidadeRepository.save(new Cidade("São Paulo", 0L, BigDecimal.ZERO, 0L, lerImagem("/imagens/iot/1.png")));
            cidadeRepository.save(new Cidade("Rio de Janeiro", 0L, BigDecimal.ZERO, 0L, lerImagem("/imagens/iot/2.png")));
            cidadeRepository.save(new Cidade("Campinas", 0L, BigDecimal.ZERO, 0L, lerImagem("/imagens/iot/3.png")));
        }
    }

    private byte[] lerImagem(String caminho) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(caminho);
        return imgFile.getInputStream().readAllBytes();
    }
}
