package com.proyecto.comparadorProyecto.controllers;


import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario usuarioGuardado = usuarioService.obtenerUsuarioPorId(usuario.getId());
        if (usuarioGuardado != null && usuarioService.verificarContrasena(usuario.getContrasena(), usuarioGuardado.getContrasena())) {
            return "Login exitoso";
        } else {
            return "Correo o contraseña incorrectos";
        }
    }
}
