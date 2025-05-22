package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favoritos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String nombre;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
