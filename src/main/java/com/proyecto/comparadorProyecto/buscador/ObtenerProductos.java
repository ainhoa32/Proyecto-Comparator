package com.proyecto.comparadorProyecto.buscador;

import com.proyecto.comparadorProyecto.dto.ProductoDto;

import java.util.List;

public interface ObtenerProductos {

    // TODO: Esto debería devolver List<List<Product>>
    public List<ProductoDto> obtenerListaSupermercado(String producto);

    public List<ProductoDto> convertirJsonALista(String respuesta);
}
