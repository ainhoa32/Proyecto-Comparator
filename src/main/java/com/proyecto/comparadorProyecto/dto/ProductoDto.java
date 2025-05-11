package com.proyecto.comparadorProyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductoDto {
    private String nombre;
    private Double precio;
    private Double precioGranel;
    private Double tamanoUnidad;
    private String unidadMedida;
    private int index;
    private String categoria;
    private String supermercado;
}
