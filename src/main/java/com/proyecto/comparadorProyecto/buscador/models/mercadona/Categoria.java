package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Categoria {
    @JsonProperty("name")
    private String nombreCategoria;
    @JsonProperty("categories")
    private List<Categoria> categoria;
}
