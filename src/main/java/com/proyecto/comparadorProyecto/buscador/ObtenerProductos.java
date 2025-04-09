package com.proyecto.comparadorProyecto.buscador;

import java.util.List;

public interface ObtenerProductos {

    public void hacerPeticionSupermercado(String producto);

    public List<List> convertirJsonALista(String responseStr);
}
