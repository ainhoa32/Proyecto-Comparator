package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cesta")
@Getter
@Setter
public class Cesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "cesta_productos",
            joinColumns = @JoinColumn(name = "cesta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
}

