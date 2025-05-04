package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.supermercados.Ahorramas;
import com.proyecto.comparadorProyecto.buscador.supermercados.Carrefour;
import com.proyecto.comparadorProyecto.buscador.supermercados.Dia;
import com.proyecto.comparadorProyecto.buscador.supermercados.Mercadona;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// TODO : refactorizar toda la clase

// Necesario para que Springboot pueda inyectarlo. Es equivalente a crear un @Bean de tipo ServicioComparador en una clase
// de tipo @Configuration
@Service
// Crea un constructor con los atributos de la clase finales no inicializados como argumentos, en este caso, dia, carrefour, y mercadona
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ComparadorService {
    private final List<Supermercado> supermercados;

    public List<ProductoDto> obtenerListaProductosComparados(String producto, String tipo) {
        return ordenarListaPorPrecio(producto, tipo);
    }

    public List<ProductoDto> ordenarListaPorPrecio(String producto, String tipo) {

        // Lanzar todas las peticiones en paralelo
        List<CompletableFuture<List<ProductoDto>>> asyncSupermercados = supermercados.stream().map(supermercado -> supermercado.obtenerListaSupermercado(producto)).toList();

        // Bloqueamos para esperar a que todas las peticiones terminen
        CompletableFuture.allOf(supermercados.toArray(new CompletableFuture[supermercados.size()])).join();

        List<ProductoDto> listaMercadona = asyncMercadona.join();
        List<ProductoDto> listaCarrefour = asyncCarrefour.join();
        List<ProductoDto> listaDia = asyncDia.join();
        List<ProductoDto> listaAhorraMas = asyncAhorraMas.join();

        // Unimos todos los resultados
        List<ProductoDto> listaTotalProductos = new ArrayList<>();
        listaTotalProductos.addAll(asyncMercadona.join());
        listaTotalProductos.addAll(asyncCarrefour.join());
        listaTotalProductos.addAll(asyncDia.join());
        listaTotalProductos.addAll(asyncAhorraMas.join());

        System.out.println(listaDia);

        // Obtengo la categoría del primer elemento que aparece al consultar un producto en el
        // indicado supermercado, con esto obtenemos la categoría del producto
        // que más relevancia tiene al hacer la búsqueda
        if (!listaTotalProductos.isEmpty()) {
            return ordenacionLista(
                    listaTotalProductos,
                    listaMercadona,
                    listaCarrefour,
                    listaDia,
                    listaAhorraMas,
                    tipo
            );
        }

        return new ArrayList<>();
    }

    public List<ProductoDto> ordenacionLista(List<ProductoDto> listaTotalProductos,
                                             List<ProductoDto> listaMercadona,
                                             List<ProductoDto> listaCarrefour,
                                             List<ProductoDto> listaDia,
                                             List<ProductoDto> listaAhorramas,
                                             String tipo){

        // Obtengo categorías prioritarias asegurándome de que las listas no estén vacías
        String categoriaPrioritariaMercadona1 = getCategoria(listaMercadona, 1);
        String categoriaPrioritariaMercadona2 = getCategoria(listaMercadona, 2);
        String categoriaPrioritariaDia1 = getCategoria(listaDia, 1);
        String categoriaPrioritariaDia2 = getCategoria(listaDia, 2);
        String categoriaPrioritariaAhorraMas1 = getCategoria(listaAhorramas, 1);
        String categoriaPrioritariaAhorraMas2 = getCategoria(listaAhorramas, 2);
        // TODO : añadir las categorías prioritarias de Carrefour y Ahorra más y ordenar según ellas

        System.out.println("---------------PRODUCTOS MEZCLADOS Y ACTUALIZADOS--------------------");
        System.out.println(listaTotalProductos);

        return listaTotalProductos.stream().sorted().limit(20).collect(Collectors.toList());
    }

}
