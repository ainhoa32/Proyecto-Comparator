package com.proyecto.comparadorProyecto.buscador.models.carrefour;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Imagen {
    @JsonProperty("food")
    private String urlImagen;
}
