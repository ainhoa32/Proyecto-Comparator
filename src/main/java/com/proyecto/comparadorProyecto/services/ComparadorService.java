package com.proyecto.comparadorProyecto.services;

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

    public List<ProductoDto> obtenerListaProductosComparados(String producto) {
        return ordenarListaPorCategoriaYPrecio(producto);
    }

    public List<ProductoDto> ordenarListaPorCategoriaYPrecio(String producto) {
        List<List<ProductoDto>> listaProductosSinComparar = new ArrayList<>();
        List<ProductoDto> listaTotalProductos = new ArrayList<>();


        List<ProductoDto> listaMercadona = mercadona.obtenerListaSupermercado(producto);
        List<ProductoDto> listaCarrefour = carrefour.obtenerListaSupermercado(producto);
        List<ProductoDto> listaDia = dia.obtenerListaSupermercado(producto);

        // Obtengo la categoría del primer elemento que aparece al consultar un producto en el
        //indicado supermercado, con esto obtenemos la categoría del producto
        // que más relevancia tiene al hacer la búsqueda

        if(listaDia.size() > 0 && listaMercadona.size() > 0 && listaCarrefour.size() > 0){
            String categoriaPrioritariaDia1 = listaDia.get(0).getCategoria1();
            String categoriaPrioritariaDia2 = listaDia.get(0).getCategoria2();
            listaProductosSinComparar.add(listaDia);

            String categoriaPrioritariaMercadona1 = listaMercadona.get(0).getCategoria1();
            String categoriaPrioritariaMercadona2 = listaMercadona.get(0).getCategoria2();
            listaProductosSinComparar.add(listaMercadona);

            listaTotalProductos = convertirListaConjunta(listaProductosSinComparar);

            if(listaTotalProductos.size() > 0){
                ordenacionLista(listaTotalProductos,
                        categoriaPrioritariaMercadona2,
                        categoriaPrioritariaMercadona1,
                        categoriaPrioritariaDia1,
                        categoriaPrioritariaDia2);
            }
        }

        return listaTotalProductos;
    }

    public List<ProductoDto> ordenacionLista(List<ProductoDto> listaTotalProductos,
                                                      String categoriaPrioritariaMercadona2,
                                                      String categoriaPrioritariaMercadona1,
                                                      String categoriaPrioritariaDia1,
                                                      String categoriaPrioritariaDia2){
        listaTotalProductos.sort(Comparator.

                comparing((ProductoDto prod) -> {
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

                .thenComparing(product -> product.getPrecioGranel()));

                System.out.println("---------------PRODUCTOS MEZCLADOS Y ACTUALIZADOS--------------------");
            System.out.println(listaTotalProductos);

            return listaTotalProductos;

    }

    public List<ProductoDto> convertirListaConjunta(List<List<ProductoDto>> listaProductosSinComparar){
        List<ProductoDto> listaTotalProductos = (List<ProductoDto>) listaProductosSinComparar.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return listaTotalProductos.subList(0, 20);
    }

}
