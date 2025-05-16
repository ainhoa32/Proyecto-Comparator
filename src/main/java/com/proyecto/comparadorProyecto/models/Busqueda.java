package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Busquedas")
@Getter
@Setter
public class Busqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_busqueda", nullable = false, length = 255)
   private String nombreBusqueda;

    @Column(name = "fecha_busqueda", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaBusqueda;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    public Busqueda() {}
}
