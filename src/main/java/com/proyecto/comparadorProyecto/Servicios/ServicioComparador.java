package com.proyecto.comparadorProyecto.Servicios;

import com.proyecto.comparadorProyecto.buscador.supermercados.Carrefour;
import com.proyecto.comparadorProyecto.buscador.supermercados.Dia;
import com.proyecto.comparadorProyecto.buscador.supermercados.Mercadona;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioComparador {

    public List<List> obtenerListaProductosComparados(String producto) {
        Mercadona mercadona = new Mercadona();
        Carrefour carrefour = new Carrefour();
        Dia dia = new Dia();
        List<List> listaComparada = new ArrayList<>();
        List<List> listaProductosTotales = new ArrayList<>();
        listaProductosTotales.add(mercadona.obtenerListaSupermercado(producto));
        listaProductosTotales.add(carrefour.obtenerListaSupermercado(producto));
        listaProductosTotales.add(dia.obtenerListaSupermercado(producto));

        List<List> listaTotales = (List<List>) listaProductosTotales.stream()
                        .flatMap(List::stream)
                                .collect(Collectors.toList());

        //Comparamos el primer campo, es decir el de precio por kg/l de cada producto
        listaTotales.sort(Comparator.comparing(prod -> (Double) prod.get(3)));

        System.out.println(listaTotales);
        return listaComparada;
    }

}
