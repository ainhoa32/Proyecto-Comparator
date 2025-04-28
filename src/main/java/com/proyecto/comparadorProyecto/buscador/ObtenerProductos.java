package com.proyecto.comparadorProyecto.buscador;

import com.proyecto.comparadorProyecto.dto.ProductoDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ObtenerProductos {

    // TODO: Esto debería devolver List<List<Product>>
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto);

    public List<ProductoDto> convertirJsonALista(String respuesta);
}
