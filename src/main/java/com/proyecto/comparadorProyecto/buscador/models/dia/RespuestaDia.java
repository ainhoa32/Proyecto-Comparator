package com.proyecto.comparadorProyecto.buscador.models.dia;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaDia {
    @JsonProperty("search_products_analytics")
    Producto codigoProducto;
}
