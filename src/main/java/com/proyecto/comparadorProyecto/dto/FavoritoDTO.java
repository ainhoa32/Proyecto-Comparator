package com.proyecto.comparadorProyecto.dto;

import java.math.BigDecimal;

public class FavoritoDTO {
    private String nombreProducto;
    private BigDecimal precio;

    public FavoritoDTO(String nombreProducto, BigDecimal precio) {
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
