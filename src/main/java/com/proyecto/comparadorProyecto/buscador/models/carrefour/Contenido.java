package com.proyecto.comparadorProyecto.buscador.models.carrefour;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contenido {
    @JsonProperty("docs")
    private List<Producto> productos;
}
