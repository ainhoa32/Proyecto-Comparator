package com.proyecto.comparadorProyecto.dto;

import lombok.NoArgsConstructor;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CestaDTO {

    private Integer id;
    private String nombreUsuario;
    private List<ProductoDto> productos;

}
