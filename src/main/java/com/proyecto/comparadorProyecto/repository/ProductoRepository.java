package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
    Optional<Producto> findByNombre(String nombre);
}
