package com.proyecto.comparadorProyecto.buscador.models.dia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaDia {
    @JsonProperty("search_products_analytics")
    private Map<String, Producto> productos;
}
