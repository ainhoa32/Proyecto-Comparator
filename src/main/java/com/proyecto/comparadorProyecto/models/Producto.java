package com.proyecto.comparadorProyecto.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "precioGranel", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioGranel;

    @Column(precision = 10, scale = 2)
    private BigDecimal tamanoUnidad;

    @Column(length = 10)
    private String unidadMedida;

    private Integer indice;

    @Lob
    private String urlImagen;

    private Integer prioridad = 0;

    @Column(length = 100)
    private String supermercado;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "producto")
    @JsonManagedReference
    private List<Busqueda> busquedas;

    @OneToMany(mappedBy = "producto")
    private List<ListaProducto> listaProductos;

    public Producto() {}

}




