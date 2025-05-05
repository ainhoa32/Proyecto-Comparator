package com.proyecto.comparadorProyecto.buscador;

import com.proyecto.comparadorProyecto.dto.ProductoDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: revisar si se puede hacer clase abstracta y poner aquí las peticiones sin que sea necesaria
public interface Supermercado {

    CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto);

}
