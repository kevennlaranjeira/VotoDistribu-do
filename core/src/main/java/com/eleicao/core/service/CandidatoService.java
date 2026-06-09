package com.eleicao.core.service;

import com.eleicao.core.dto.CandidatoDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;


    @Transactional(readOnly = true)
    public List<CandidatoDTO> listarPorQuantidadeVotosDesc() {
        List<Candidato> candidatoList = candidatoRepository.findAllByOrderByQuantidadeVotosDesc();

        return candidatoList.stream()
                .map(c -> {
                    CandidatoDTO dto = new CandidatoDTO();
                    dto.setId(c.getId());
                    dto.setNome(c.getNome());
                    dto.setQuantidadeVotos(c.getQuantidadeVotos());
                    dto.setPorcentagem(c.getPorcentagem());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Candidato findById(Long id) {
        return candidatoRepository.findById(id).orElseThrow();
    }
}
