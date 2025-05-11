package com.proyecto.comparadorProyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
