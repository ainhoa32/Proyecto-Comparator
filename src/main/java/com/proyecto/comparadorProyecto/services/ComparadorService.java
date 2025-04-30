package com.proyecto.comparadorProyecto.services;

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

// TODO : refactorizar toda la clase

// Necesario para que Springboot pueda inyectarlo. Es equivalente a crear un @Bean de tipo ServicioComparador en una clase
// de tipo @Configuration
@Service
// Crea un constructor con los atributos de la clase finales no inicializados como argumentos, en este caso, dia, carrefour, y mercadona
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ComparadorService {
    private final Mercadona mercadona;
    private final Carrefour carrefour;
    private final Dia dia;
    private final Ahorramas ahorramas;

    public List<ProductoDto> obtenerListaProductosComparados(String producto, String tipo) {
        return ordenarListaPorPrecio(producto, tipo);
    }

    public List<ProductoDto> ordenarListaPorPrecio(String producto, String tipo) {

        // Lanzar todas las peticiones en paralelo
        CompletableFuture<List<ProductoDto>> asyncMercadona = mercadona.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> asyncCarrefour = carrefour.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> asyncDia = dia.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> asyncAhorraMas = ahorramas.obtenerListaSupermercado(producto);

        // Bloqueamos para esperar a que todas las peticiones terminen
        CompletableFuture.allOf(asyncMercadona, asyncCarrefour, asyncDia, asyncAhorraMas).join();

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

        System.out.println(listaAhorraMas);

        // Obtengo la categoría del primer elemento que aparece al consultar un producto en el
        // indicado supermercado, con esto obtenemos la categoría del producto
        // que más relevancia tiene al hacer la búsqueda
        if (!listaTotalProductos.isEmpty()) {
            // Obtengo categorías prioritarias asegurándome de que las listas no estén vacías
            String catMercadona1 = listaMercadona.isEmpty() ? "" : listaMercadona.get(0).getCategoria1();
            String catMercadona2 = listaMercadona.isEmpty() ? "" : listaMercadona.get(0).getCategoria2();
            String catDia1 = listaDia.isEmpty() ? "" : listaDia.get(0).getCategoria1();
            String catDia2 = listaDia.isEmpty() ? "" : listaDia.get(0).getCategoria2();

            return ordenacionLista(
                    listaTotalProductos,
                    catMercadona2,
                    catMercadona1,
                    catDia1,
                    catDia2,
                    tipo
            );
        }

        return new ArrayList<>();
    }



    public List<ProductoDto> ordenacionLista(List<ProductoDto> listaTotalProductos,
                                                      String categoriaPrioritariaMercadona2,
                                                      String categoriaPrioritariaMercadona1,
                                                      String categoriaPrioritariaDia1,
                                                      String categoriaPrioritariaDia2,
                                             String tipo){

        // TODO : añadir las categorías prioritarias de Carrefour y Ahorra más y ordenar según ellas
        listaTotalProductos.sort(Comparator.
                comparing((ProductoDto prod) -> {
                    System.out.println(prod.getNombre() + prod.getSupermercado());
                    String categoria = prod.getCategoria1();
                    String supermercado = prod.getSupermercado();

                    // Si es igual a true se queda más abajo en la lista
                    if (supermercado.equalsIgnoreCase("DIA")) {
                        return !categoriaPrioritariaDia1.equalsIgnoreCase(categoria);
                    } else if (supermercado.equalsIgnoreCase("MERCADONA")) {
                        return !categoriaPrioritariaMercadona1.equalsIgnoreCase(categoria);
                    } else {
                        return true;
                    }
                })

                .thenComparing((ProductoDto prod) -> {
                    String categoria2 = prod.getCategoria2();
                    String supermercado = prod.getSupermercado();

                    if (supermercado.equalsIgnoreCase("DIA")) {
                        return !categoriaPrioritariaDia2.equalsIgnoreCase(categoria2);
                    } else if (supermercado.equalsIgnoreCase("MERCADONA")) {
                        return !categoriaPrioritariaMercadona2.equalsIgnoreCase(categoria2);
                    } else {
                        return true;
                    }
                })

                .thenComparing(product -> tipo.equals("precioGranel") ? product.getPrecioGranel() : product.getPrecio() ));

        System.out.println("---------------PRODUCTOS MEZCLADOS Y ACTUALIZADOS--------------------");
        System.out.println(listaTotalProductos);

        return listaTotalProductos.size() == 0 ? listaTotalProductos : listaTotalProductos.subList(0, listaTotalProductos.size() >= 20 ? 20 : listaTotalProductos.size());
    }


}
