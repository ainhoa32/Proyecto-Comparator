package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit {
    //Mapea el objeto del json llamado display_name
    @JsonProperty("display_name")
    public String nombre;
    @JsonProperty("price_instructions")
    public PriceInstructions price_instructions;
}
