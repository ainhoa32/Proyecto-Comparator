package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritosRepository  extends CrudRepository<Favoritos, Integer> {
    List<Favoritos> findByUsuario(Usuario usuario);
    void deleteByUsuarioAndNombre(Usuario usuario, String nombre);
    List<Favoritos> findByUsuarioAndNombre(Usuario usuario, String nombre);

}
