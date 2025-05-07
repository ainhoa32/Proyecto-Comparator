package com.proyecto.comparadorProyecto.buscador;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculadorPrioridad {
    // Comparo las categorías priritarias (las categorías mas relevantes respecto a la búsqueda)
    // con las categorías de un producto.
    // En el caso de que coincida la categorías más prioritaria se devuelve un 0
    // En el caso de que coincida con otra devuelve un número más alto
    // En el caso de que no coincida con ninguna devuelve un número muy alto
    public int calcularSegunCategorias(List<String> categoriasProducto, List<String> categoriasPrioritarias) {
        for (int i = 0; i < categoriasPrioritarias.size(); i++) {
            if (categoriasProducto.get(i).equals(categoriasPrioritarias.get(i))) {
                return i;
            }
        }

        return 100;
    }

    public int calcularSegunIndex(int index){
        if(index <= 3){
            return 0;
        }else if(index <= 6){
            return 1;
        }
        return 100;
    }
}
