package com.proyecto.comparadorProyecto.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "listasPredeterminadas")
public class ListasPredeterminada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name="esVisible", nullable = false)
    private boolean esVisible;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ListaProducto> listaProductos;

    public boolean getEsVisible() {
        return esVisible ? true : false;
    }
}