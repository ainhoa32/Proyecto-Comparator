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
import java.util.stream.Collectors;

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
        CompletableFuture<List<ProductoDto>> futuroMercadona = mercadona.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> futuroCarrefour = carrefour.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> futuroDia = dia.obtenerListaSupermercado(producto);

        CompletableFuture<List<ProductoDto>> futuroAhorraMas = ahorramas.obtenerListaSupermercado(producto);

        // Bloqueamos para esperar a que todas las peticiones terminen
        CompletableFuture.allOf(futuroMercadona, futuroCarrefour, futuroDia, futuroAhorraMas).join();

        List<ProductoDto> listaMercadona = futuroMercadona.join();
        List<ProductoDto> listaCarrefour = futuroCarrefour.join();
        List<ProductoDto> listaDia = futuroDia.join();
        List<ProductoDto> listaAhorraMas = futuroAhorraMas.join();

        // Unimos todos los resultados
        List<ProductoDto> listaTotalProductos = new ArrayList<>();
        listaTotalProductos.addAll(futuroMercadona.join());
        listaTotalProductos.addAll(futuroCarrefour.join());
        listaTotalProductos.addAll(futuroDia.join());
        listaTotalProductos.addAll(futuroAhorraMas.join());

//        List<ProductoDto> listaOrdenada = ordenacionLista(
//                listaTotalProductos,
//                lis
//                tipo
//        );

        List<ProductoDto> listaProductosReducida = listaTotalProductos.size() == 0 ? listaTotalProductos : listaTotalProductos.subList(0, listaTotalProductos.size() >= 20 ? 20 : listaTotalProductos.size());


        // Obtengo la categoría del primer elemento que aparece al consultar un producto en el
        // indicado supermercado, con esto obtenemos la categoría del producto
        // que más relevancia tiene al hacer la búsqueda
        if (!listaProductosReducida.isEmpty()) {
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

//    public String obtenerCategorias(List<ProductoDto>){
//
//    }


    public List<ProductoDto> ordenacionLista(List<ProductoDto> listaTotalProductos,
//                                                      List<ProductoDto> listaMercadona,
//                                                      List<ProductoDto> listaCarrefour,
//                                                      List<ProductoDto> listaDia,
//                                                      List<ProductoDto> listaAhorraMas,
                                                      String categoriaPrioritariaMercadona2,
                                                      String categoriaPrioritariaMercadona1,
                                                      String categoriaPrioritariaDia1,
                                                      String categoriaPrioritariaDia2,
                                             String tipo){
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
                        return true; // En nuestro caso no hemos implementado la ordenación
                        // con el Carrefour porque no es funcional en algunos casos
                        // (otros ordenadores)
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

            return listaTotalProductos;

    }


}
