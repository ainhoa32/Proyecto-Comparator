package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Necesario para que Springboot pueda inyectarlo. Es equivalente a crear un @Bean de tipo ServicioComparador en una clase
// de tipo @Configuration o el @Component
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
        List<CompletableFuture<List<ProductoDto>>> asyncSupermercados = supermercados
                .stream()
                .map(supermercado -> supermercado.obtenerListaSupermercado(producto)
                        .exceptionally(e -> {
                            System.err.println("Error en " + supermercado.getClass().getSimpleName());
                            e.printStackTrace();
                            return Collections.emptyList();
                        }))
                .toList();

        // Bloqueamos para esperar a que todas las peticiones terminen
        CompletableFuture.allOf(asyncSupermercados.toArray(new CompletableFuture[supermercados.size()])).join();

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

        return listaProductos.stream().limit(20).collect(Collectors.toList());
    }

}
