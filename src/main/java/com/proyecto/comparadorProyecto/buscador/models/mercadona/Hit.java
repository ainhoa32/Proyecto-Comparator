package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit {
    @JsonProperty("categories")
    private List<Categoria> categoria;
    //Mapea el objeto del json llamado display_name
    @JsonProperty("thumbnail")
    private String urlImagen;
    @JsonProperty("display_name")
    private String nombre;
    @JsonProperty("price_instructions")
    private PriceInstructions priceInstructions;
}
