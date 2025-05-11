package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodosUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
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
        return BCrypt.checkpw(contrasenaRecibida, contrasenaGuardada);
    }

    private String cifrarContrasena(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }



}
