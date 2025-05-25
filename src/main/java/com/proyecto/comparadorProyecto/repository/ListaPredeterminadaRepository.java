package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Listaspredeterminada;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListaPredeterminadaRepository  extends CrudRepository<Listaspredeterminada, Integer> {
    Optional<Listaspredeterminada> findByNombre(String nombre);
}
