package com.proyecto.comparadorProyecto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ListaProductoId implements Serializable {
    private static final long serialVersionUID = -2747309038622458179L;
    @Column(name = "lista_id", nullable = false)
    private Integer listaId;

    @Column(name = "producto_id", nullable = false)
    private Integer productoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListaProductoId entity = (ListaProductoId) o;
        return Objects.equals(this.listaId, entity.listaId) &&
                Objects.equals(this.productoId, entity.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listaId, productoId);
    }

}