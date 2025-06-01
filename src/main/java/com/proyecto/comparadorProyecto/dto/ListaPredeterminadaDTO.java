package com.proyecto.comparadorProyecto.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ListaPredeterminadaDTO {
    private String nombre;
    private List<ProductoDto> productos;

}
