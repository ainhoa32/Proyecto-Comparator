package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CestaRepository extends CrudRepository <Cesta, Integer> {
    Optional<Cesta> findByUsuario(Usuario usuario);
}
