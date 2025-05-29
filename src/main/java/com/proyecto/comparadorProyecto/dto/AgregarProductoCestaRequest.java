package com.proyecto.comparadorProyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgregarProductoCestaRequest {

    @NotNull
    @JsonProperty("usuario")
    private String nombreUsuario;

    @NotNull
    @JsonProperty("prod")
    private ProductoDto producto;
}
