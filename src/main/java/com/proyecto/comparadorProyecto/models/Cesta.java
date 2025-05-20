package com.proyecto.comparadorProyecto.models;

import com.proyecto.comparadorProyecto.models.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cesta")
@Getter
@Setter
@NoArgsConstructor
public class Cesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "cesta_productos",
            joinColumns = @JoinColumn(name = "cesta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;
}

