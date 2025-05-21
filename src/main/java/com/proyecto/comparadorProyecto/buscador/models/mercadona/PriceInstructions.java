package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceInstructions {
    @JsonProperty("unit_price")
    private double precioUnidad;
    @JsonProperty("bulk_price")
    private double precioGranel;
    @JsonProperty("unit_size")
    private double tamanoUnidad;
    @JsonProperty("size_format")
    private String unidadMedida;
}
