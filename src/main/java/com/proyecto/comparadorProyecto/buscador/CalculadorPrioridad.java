package com.proyecto.comparadorProyecto.buscador;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculadorPrioridad {
    public int calcularSegunCategorias(List<String> categoriasProducto, List<String> categoriasPrioritarias) {
        for (int i = 0; i < categoriasPrioritarias.size(); i++) {
            if (categoriasProducto.contains(categoriasPrioritarias.get(i))) {
                return i;
            }
        }

        return 100;
    }
}
