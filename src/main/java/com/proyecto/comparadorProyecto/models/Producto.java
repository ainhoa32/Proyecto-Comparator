package com.proyecto.comparadorProyecto.models;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(nullable = false)
    @Getter @Setter
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    @Getter @Setter
    private BigDecimal precio;

    @Column(nullable = false, precision = 10, scale = 2)
    @Getter @Setter
    private BigDecimal precioGranel;

    @Column(precision = 10, scale = 2)
    @Getter @Setter
    private BigDecimal tamanoUnidad;

    @Column(length = 10)
    @Getter @Setter
    private String unidadMedida;

    @Getter @Setter
    private Integer indice;

    @Lob
    @Getter @Setter
    private String urlImagen;

    @Getter @Setter
    private Integer prioridad = 0;

    @Column(length = 100)
    @Getter @Setter
    private String supermercado;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Getter @Setter
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "busqueda_id", referencedColumnName = "id")
    @Getter @Setter
    private Busqueda busqueda;

    @ManyToMany(mappedBy = "productos")
    @Getter
    @Setter
    private Set<ListaPredeterminada> listas;


    // Constructor por defecto
    public Producto() {}

}




