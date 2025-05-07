package com.proyecto.comparadorProyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ProductoDto implements Comparable<ProductoDto> {
    String nombre;
    Double precio;
    Double precioGranel;
    Double tamanoUnidad;
    String unidadMedida;
    int index;
    String urlImagen;
    int prioridad;
    String supermercado;

    @Override
    public int compareTo(ProductoDto o) {
        if (this.prioridad > o.prioridad) {
            return 1;
        }

        if (this.prioridad < o.prioridad) {
            return -1;
        }

        return (int) Math.ceil(this.precioGranel - o.precioGranel);
    }
}
