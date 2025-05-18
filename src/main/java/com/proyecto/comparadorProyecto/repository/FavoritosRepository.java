package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Favoritos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoritosRepository  extends CrudRepository<Favoritos, Integer> {
    List<Favoritos> findByUsuarioId(int id);
}
