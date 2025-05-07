package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        // Aquí deberías validar el usuario y la contraseña (desde una base de datos por ejemplo)
        if (loginRequest.getUsername().equals("admin") && loginRequest.getPassword().equals("password")) {
            return jwtTokenProvider.generateToken(loginRequest.getUsername());
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}

class LoginRequest {
    private String username;
    private String password;

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
