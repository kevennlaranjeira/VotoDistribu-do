package com.eleicao.sd.controller;

import com.eleicao.sd.dto.UsuarioDTO;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.service.UserService;
import com.eleicao.sd.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<String> gerarToken(@RequestBody UsuarioDTO user) {
        if (userService.verificarSenha(user.getNick(), user.getSenha())) {
            String token = jwtUtil.generateToken(user.getNick());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas.");
    }

    @PostMapping
    public ResponseEntity<?> criarUser(@RequestBody UsuarioDTO user) {
        try {
            Usuario usuarioCriado = userService.createUser(user);
            String token = jwtUtil.generateToken(user.getNick());
            return ResponseEntity.status(201).body(token);
        } catch (RuntimeException e) {
                return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsers(@RequestHeader("Authorization") String token){
        String nick = jwtUtil.getNickFromToken(token.replace("Bearer ", ""));
        if(nick.equals("yan")){
            List<Usuario> list = userService.buscarUsers();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Vivo");
    }
}
