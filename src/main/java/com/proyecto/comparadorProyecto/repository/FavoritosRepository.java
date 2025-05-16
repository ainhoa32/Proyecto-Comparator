package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Favoritos;
import org.springframework.data.repository.CrudRepository;

public interface FavoritosRepository  extends CrudRepository<Favoritos, Integer> {
}
