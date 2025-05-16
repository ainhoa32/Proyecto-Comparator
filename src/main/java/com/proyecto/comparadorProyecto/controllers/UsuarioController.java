package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.LoginRequest;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.services.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Intentando login con Nombre: {}", loginRequest.getNombre());

        Usuario usuarioGuardado = usuarioServicio.obtenerUsuarioPorNombre(loginRequest.getNombre());

        if (usuarioGuardado == null) {
            logger.warn("Usuario no encontrado: {}", loginRequest.getNombre());
            return new ResponseEntity<>("Nombre de usuario no encontrado", HttpStatus.BAD_REQUEST);
        }

        if (usuarioServicio.verificarContrasena(loginRequest.getContrasena(), usuarioGuardado.getContrasena())) {
            logger.info("Login exitoso para el usuario: {}", loginRequest.getNombre());
            return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
        } else {
            logger.warn("Contraseña incorrecta para el usuario: {}", loginRequest.getNombre());
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
    }
}
