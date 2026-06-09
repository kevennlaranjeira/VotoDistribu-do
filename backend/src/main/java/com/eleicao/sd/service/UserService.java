package com.eleicao.sd.service;

import com.eleicao.sd.dto.UsuarioDTO;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario createUser(UsuarioDTO usuarioDTO) {

        if (usuarioDTO.getNick().contains(" ")) {
            throw new RuntimeException("Nick não pode conter espaços");
        }

        if (!usuarioDTO.getNick().matches("^[a-zA-Z0-9_-]{3,20}$")) {
            throw new RuntimeException("Nick inválido. Use apenas letras, números, _ ou - (3 a 20 caracteres)");
        }

        if (usuarioRepository.existsByNick(usuarioDTO.getNick())) {
            throw new RuntimeException("O Nick já está em uso.");
        }

        String senha = usuarioDTO.getSenha();
        if (senha.length() < 6 || senha.length() > 16) {
            throw new RuntimeException("Senha inválida. Use de 6 a 16 caracteres.");
        }

        Usuario usuario = new Usuario();
        usuario.setNick(usuarioDTO.getNick());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha())); // senha criptografada

        return usuarioRepository.save(usuario);
    }

    public boolean verificarSenha(String nick, String senha) {
        Usuario user = usuarioRepository.findByNick(nick).orElse(null);
        if (user == null) return false;
        return passwordEncoder.matches(senha, user.getSenha());
    }

    public List<Usuario> buscarUsers() {
        return usuarioRepository.findAll();
    }


}
