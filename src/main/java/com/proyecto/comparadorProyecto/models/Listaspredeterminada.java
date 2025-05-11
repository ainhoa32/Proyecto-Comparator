package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "listaspredeterminadas")
public class Listaspredeterminada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    @Getter
    @Setter
    private String nombre;

}