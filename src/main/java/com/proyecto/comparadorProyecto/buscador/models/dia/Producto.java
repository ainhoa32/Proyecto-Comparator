package com.proyecto.comparadorProyecto.buscador.models.dia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producto {
    @JsonProperty("index")
    private int index;
    @JsonProperty("item_name")
    private String nombre;
    @JsonProperty("price")
    private double precio;
    @JsonProperty("item_category2")
    private String categoria1;
    @JsonProperty("item_category")
    private String categoria2;
}
