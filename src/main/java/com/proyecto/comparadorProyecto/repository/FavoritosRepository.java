package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoritosRepository  extends JpaRepository<Favoritos, Integer> {
    List<Favoritos> findByUsuario(Usuario usuario);
    int deleteByUsuarioAndNombre(Usuario usuario, String nombre);
    List<Favoritos> findByUsuarioAndNombre(Usuario usuario, String nombre);

}
