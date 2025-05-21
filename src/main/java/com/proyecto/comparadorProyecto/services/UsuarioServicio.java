package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setContrasena(cifrarContrasena(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public boolean verificarContrasena(String contrasenaRecibida, String contrasenaGuardada) {
        return passwordEncoder.matches(contrasenaRecibida, contrasenaGuardada);     }

    private String cifrarContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }

    public Usuario obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public Boolean usuarioExiste(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }
}
