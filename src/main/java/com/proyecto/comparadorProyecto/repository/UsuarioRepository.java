package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {
    Usuario findByCorreo(String correo);
}
