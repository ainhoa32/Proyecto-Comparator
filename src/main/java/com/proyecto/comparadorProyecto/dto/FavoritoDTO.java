package com.proyecto.comparadorProyecto.dto;

public class FavoritoDTO {
    private Integer usuarioId;
    private String nombre;

    public FavoritoDTO() {}

    public FavoritoDTO(Integer usuarioId, String nombre) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
