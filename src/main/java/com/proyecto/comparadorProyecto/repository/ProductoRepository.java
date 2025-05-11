package com.proyecto.comparadorProyecto.repository;

import com.proyecto.comparadorProyecto.models.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
}
