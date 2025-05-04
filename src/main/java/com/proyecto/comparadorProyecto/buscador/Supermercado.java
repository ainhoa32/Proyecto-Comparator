package com.proyecto.comparadorProyecto.buscador;

import com.proyecto.comparadorProyecto.dto.ProductoDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Supermercado {

    CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto);
}
