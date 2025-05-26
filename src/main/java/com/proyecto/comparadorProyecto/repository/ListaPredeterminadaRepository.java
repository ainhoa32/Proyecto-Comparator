package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Listaspredeterminada;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ListaPredeterminadaRepository  extends CrudRepository<Listaspredeterminada, Integer> {
    Optional<Listaspredeterminada> findByNombre(String nombre);

    List<Listaspredeterminada> findAll();

    List<Listaspredeterminada> findByEsVisible(boolean esVisible);
}
