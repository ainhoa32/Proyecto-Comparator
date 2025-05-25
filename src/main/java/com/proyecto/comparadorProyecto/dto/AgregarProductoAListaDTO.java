package com.proyecto.comparadorProyecto.dto;

import com.proyecto.comparadorProyecto.models.Producto;
import lombok.Data;

@Data
public class AgregarProductoAListaDTO {
    private String nombre;
    private ProductoDto producto;

}
