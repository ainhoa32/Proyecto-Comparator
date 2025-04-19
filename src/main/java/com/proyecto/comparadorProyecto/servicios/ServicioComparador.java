package com.proyecto.comparadorProyecto.servicios;

import com.proyecto.comparadorProyecto.buscador.supermercados.Carrefour;
import com.proyecto.comparadorProyecto.buscador.supermercados.Dia;
import com.proyecto.comparadorProyecto.buscador.supermercados.Mercadona;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Necesario para que Springboot pueda inyectarlo. Es equivalente a crear un @Bean de tipo ServicioComparador en una clase
// de tipo @Configuration
@Component
// Crea un constructor con los atributos de la clase finales no inicializados como argumentos, en este caso, dia, carrefour, y mercadona
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ServicioComparador {
    private final Mercadona mercadona;
    private final Carrefour carrefour;
    private final Dia dia;

    public List<List> obtenerListaProductosComparados(String producto) {
        List<List> listaComparada = new ArrayList<>();
        List<List> listaProductosTotales = new ArrayList<>();
        listaProductosTotales.add(mercadona.obtenerListaSupermercado(producto));
        listaProductosTotales.add(carrefour.obtenerListaSupermercado(producto));
        listaProductosTotales.add(dia.obtenerListaSupermercado(producto));

        List<List> listaTotales = (List<List>) listaProductosTotales.stream()
                        .flatMap(List::stream)
                                .collect(Collectors.toList());

        //Comparamos el primer campo, es decir el de precio por kg/l de cada producto
        listaTotales.sort(Comparator.comparing(prod -> (Double) prod.get(2)));

        System.out.println("---------------PRODUCTOS MEZCLADOS Y ACTUALIZADOS--------------------");
        listaTotales.forEach(productoBuscado -> {
            System.out.println(productoBuscado);
        });

        return listaTotales;
    }

}
