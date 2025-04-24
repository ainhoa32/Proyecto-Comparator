package com.proyecto.comparadorProyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductoDto {
    private String nombre;
    private Double precio;
    private Double precioGranel;
    private Double tamanoUnidad;
    private String unidadMedida;
    private int index;
    private String categoria1;
    private String categoria2;
    private String supermercado;
}
