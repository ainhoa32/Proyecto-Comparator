package com.proyecto.comparadorProyecto.controllers;


import com.proyecto.comparadorProyecto.dto.LoginRequest;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.services.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {


    @Autowired
    private UsuarioServicio usuarioServicio;


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Intentando login con:");
        System.out.println("Nombre: " + loginRequest.getNombre());
        System.out.println("Contraseña: " + loginRequest.getContrasena());
        Usuario usuarioGuardado = usuarioServicio.obtenerUsuarioPorNombre(loginRequest.getNombre());

        // Verifica que exista y que la contraseña coincida
        if (usuarioGuardado != null &&
                usuarioServicio.verificarContrasena(loginRequest.getContrasena(), usuarioGuardado.getContrasena())) {
            return "Login exitoso";
        } else {
            return "Nombre de usuario o contraseña incorrectos";
        }
    }
}
