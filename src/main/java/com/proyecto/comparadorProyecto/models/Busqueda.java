package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Busquedas")
public class Busqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(name = "nombre_busqueda", nullable = false, length = 255)
    @Getter
    @Setter
    private String nombreBusqueda;

    @Column(name = "fecha_busqueda", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Getter
    @Setter
    private LocalDateTime fechaBusqueda;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Producto producto;

    // Constructor por defecto
    public Busqueda() {}
}
