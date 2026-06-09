package com.eleicao.core.controller;

import com.eleicao.core.component.CandidatoPublisher;
import com.eleicao.core.dto.CandidatoDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/eleicao-gp2")
public class EleicaoGP2Controller {

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private CandidatoPublisher candidatoPublisher;

    @GetMapping("/listarCandidatosDesc")
    public ResponseEntity<List<CandidatoDTO>> listarCandidatosDesc(){
        List<CandidatoDTO> lista = candidatoService.listarPorQuantidadeVotosDesc();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/candidatos/{id}/imagem")
    public ResponseEntity<byte[]> buscarImagem(@PathVariable Long id) {
        Candidato candidato = candidatoService.findById(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG) // ou IMAGE_PNG dependendo da imagem
                .body(candidato.getFoto());
    }
}
