package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.LoginRequest;
import com.proyecto.comparadorProyecto.model.Usuario;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import com.proyecto.comparadorProyecto.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, UsuarioRepository usuarioRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        return usuarioRepository.findByCorreo(loginRequest.getUsername())
                .filter(usuario -> usuario.getContraseña().equals(loginRequest.getPassword()))
                .map(usuario -> jwtTokenProvider.generateToken(usuario.getCorreo()))
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}
