package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lista_productos")
public class ListaProducto {
    @EmbeddedId
    private ListaProductoId id;

    @MapsId("productoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @Getter
    @Setter
    private Producto producto;

}