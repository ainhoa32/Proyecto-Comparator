package com.proyecto.comparadorProyecto.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JwtTestHelper {

    private final JwtUtil jwtUtil;

    public JwtTestHelper(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    public void testToken() {
        System.out.println("Clave JWT (bytes): " + jwtUtil.getSecretLength());

        String token = jwtUtil.generarToken("usuarioTest");
        System.out.println("Token generado: " + token);
        System.out.println("Username extraído: " + jwtUtil.extraerNombreUsuario(token));
        System.out.println("¿Token válido? " + jwtUtil.validarToken(token));
    }
}
