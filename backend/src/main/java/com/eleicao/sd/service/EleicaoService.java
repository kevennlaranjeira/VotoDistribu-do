package com.eleicao.sd.service;

import com.eleicao.sd.component.VotoSender;
import com.eleicao.sd.dto.VotoDTO;
import com.eleicao.sd.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EleicaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LogManager.getLogger(EleicaoService.class);

    private final VotoSender votoSender;

    public EleicaoService(VotoSender votoSender) {
        this.votoSender = votoSender;
    }

    public String votar(String nick, Long id_candidato) {
        if (!usuarioRepository.existsByNick(nick)) {
            logger.error("Erro ao votar - User nao encontrado: {}", nick);
            throw new RuntimeException("Usuário não encontrado");
        }
        logger.info("Usuário encontrado: {} - Processando voto.", nick);

        LocalDateTime agora = LocalDateTime.now();

        VotoDTO voto = new VotoDTO();
        voto.setType("eleicao-gp2");
        voto.setObject(id_candidato.toString()); //passando o id do candidato
        voto.setValor(1L);
        voto.setDatetime(agora);

        votoSender.enviarVoto(voto);
        return "Votou em " + id_candidato.toString();
    }
}
