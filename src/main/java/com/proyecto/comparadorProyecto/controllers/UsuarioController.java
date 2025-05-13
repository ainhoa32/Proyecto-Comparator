package com.proyecto.comparadorProyecto.controllers;


import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioServicio usuarioServicio;


    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario usuarioGuardado = usuarioServicio.obtenerUsuarioPorId(usuario.getId());
        if (usuarioGuardado != null && usuarioServicio.verificarContrasena(usuario.getContrasena(), usuarioGuardado.getContrasena())) {
            return "Login exitoso";
        } else {
            return "Correo o contraseña incorrectos";
        }
    }
}
