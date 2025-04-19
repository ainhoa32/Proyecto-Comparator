package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceInstructions {
    @JsonProperty("unit_price")
    public String precioUnidad;
    @JsonProperty("bulk_price")
    public double precioGranel;
    @JsonProperty("unit_size")
    public double tamanoUnidad;
    @JsonProperty("size_format")
    public String unidadMedida;
}
