package com.proyecto.comparadorProyecto.dto;

import com.proyecto.comparadorProyecto.models.Producto;
import lombok.Getter;
import lombok.Setter;

public class FavoritoDTO {
    @Setter
    @Getter
    private String usuario;

    @Setter
    @Getter
    private String nombreBusqueda;

    public FavoritoDTO() {}

    public FavoritoDTO(String user, String nombreBusqueda) {
        this.usuario = user;
        this.nombreBusqueda = nombreBusqueda;
    }

}
