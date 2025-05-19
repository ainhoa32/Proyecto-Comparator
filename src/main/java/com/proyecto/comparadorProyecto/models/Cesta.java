package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cesta")
@Getter
@Setter
public class Cesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_prods", length = 1000)
    private String idProds;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
}

