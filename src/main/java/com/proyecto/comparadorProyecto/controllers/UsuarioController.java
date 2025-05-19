package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.LoginRequest;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.security.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuarioGuardado = usuarioServicio.obtenerUsuarioPorNombre(loginRequest.getNombre());

        if (usuarioGuardado == null) {
            return new ResponseEntity<>("Nombre de usuario no encontrado", HttpStatus.BAD_REQUEST);
        }

        if (usuarioServicio.verificarContrasena(loginRequest.getContrasena(), usuarioGuardado.getContrasena())) {
            String token = jwtUtil.generarToken(usuarioGuardado.getNombre());
            return ResponseEntity.ok().body(token);
        } else {
            return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        System.out.println(usuario.getNombre());
        if (usuarioServicio.usuarioExiste(usuario.getNombre())) {
            return new ResponseEntity<>("El nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        Usuario nuevoUsuario = usuarioServicio.guardarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
}
