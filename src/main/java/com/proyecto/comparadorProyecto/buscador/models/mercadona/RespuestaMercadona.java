package com.proyecto.comparadorProyecto.buscador.models.mercadona;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaMercadona {
    @JsonProperty("hits")
    private List<Producto> producto;
}
