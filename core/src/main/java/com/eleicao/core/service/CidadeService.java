package com.eleicao.core.service;

import com.eleicao.core.dto.CidadeDTO;
import com.eleicao.core.entity.Cidade;
import com.eleicao.core.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<CidadeDTO> listAll(){
        List<Cidade> cidadeList = cidadeRepository.findAll();

        return cidadeList.stream()
                .map(c -> {
                    CidadeDTO dto = new CidadeDTO();
                    dto.setNome(c.getNome());
                    dto.setValor_qualidade_ar(c.getValor_qualidade_ar());
                    dto.setMedia(c.getMedia());
                    dto.setMediana(c.getMediana());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
