package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String correo);  // Para encontrar usuario por correo
    Usuario findByNombre(String nombre);  // Para encontrar usuario por nombre
    boolean existsByNombre(String nombre); // Para verificar si el nombre ya existe
}
