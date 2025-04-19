package com.proyecto.comparadorProyecto.buscador;

import java.util.List;

public interface ObtenerProductos {

    public List<List> obtenerListaSupermercado(String producto);

    public List<List> convertirJsonALista(String respuesta);
}
