package com.proyecto.comparadorProyecto.dto;

import com.proyecto.comparadorProyecto.models.Producto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoritoDTO {

    private String usuario;

    private String nombreBusqueda;

    public FavoritoDTO() {}

    public FavoritoDTO(String user, String nombreBusqueda) {
        this.usuario = user;
        this.nombreBusqueda = nombreBusqueda;
    }

}
