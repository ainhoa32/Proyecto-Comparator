package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.ListasPredeterminada;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ListaPredeterminadaRepository  extends CrudRepository<ListasPredeterminada, Integer> {
    Optional<ListasPredeterminada> findByNombre(String nombre);

    List<ListasPredeterminada> findAll();

    List<ListasPredeterminada> findByEsVisible(boolean esVisible);
}
