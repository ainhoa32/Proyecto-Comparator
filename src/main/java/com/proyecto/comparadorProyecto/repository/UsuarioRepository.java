package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
