package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Necesario para que Springboot pueda inyectarlo. Es equivalente a crear un @Bean de tipo ServicioComparador en una clase
// de tipo @Configuration o el @Component
@Service
// Crea un constructor con los atributos de la clase finales no inicializados como argumentos, en este caso, dia, carrefour, y mercadona
@RequiredArgsConstructor
public class ComparadorService {
    private final List<Supermercado> supermercados;

    public List<ProductoDto> obtenerListaProductosComparadosTotal(String producto) {
        return ordenarListaPorPrecio(producto, supermercados);
    }

    public List<ProductoDto> obtenerListaProductosComparadosConcreto(String producto, String supermercadosString) {
        return ordenarListaPorPrecio(producto, obtenerSupermercados(supermercadosString));
    }

    public List<ProductoDto> ordenarListaPorPrecio(String producto, List<Supermercado> listaSupermercados) {
        // Lanzar todas las peticiones en paralelo
        List<CompletableFuture<List<ProductoDto>>> asyncSupermercados = listaSupermercados
                .stream()
                .map(supermercado -> supermercado.obtenerListaSupermercado(producto)
                        .exceptionally(e -> {
                            System.err.println("Error en " + supermercado.getClass().getSimpleName());
                            e.printStackTrace();
                            return Collections.emptyList();
                        }))
                .toList();

        // Bloqueamos para esperar a que todas las peticiones terminen
        CompletableFuture.allOf(asyncSupermercados.toArray(new CompletableFuture[listaSupermercados.size()])).join();

        // Obtenemos todos los resultados y lo incluimos en una única lista
        List<ProductoDto> listaCombinadaSupermercados = asyncSupermercados.stream()
                .flatMap(lista -> lista.join().stream())
                .collect(Collectors.toList());

        return ordenacionLista(listaCombinadaSupermercados);
    }

    public List<ProductoDto> ordenacionLista(List<ProductoDto> listaProductos) {
        listaProductos.sort(Comparator
                .comparing((ProductoDto prod) -> prod.getPrioridad())
                .thenComparing((ProductoDto producto) -> producto.getPrecioGranel()));

        return listaProductos.stream().collect(Collectors.toList());
    }

    public List<Supermercado> obtenerSupermercados(String supermercadosString){
        List<String> supermercadosSeleccionados = Arrays.stream(supermercadosString.split("-")).toList();
        List<Supermercado> filtrados = supermercados.stream()
                .filter(s -> {
                    String nombre = s.getClass().getSimpleName();
                    return supermercadosSeleccionados.contains(nombre);
                })
                .collect(Collectors.toList());
        return filtrados;
    }

}
