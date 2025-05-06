package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.CalculadorPrioridad;
import com.proyecto.comparadorProyecto.buscador.ClienteHttp;
import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.models.carrefour.Producto;
import com.proyecto.comparadorProyecto.buscador.models.carrefour.RespuestaCarrefour;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


// TODO: Arreglar Carrefour para que funcione en cualquier ordenador
@Component
@RequiredArgsConstructor
public class Carrefour implements Supermercado {
    private final ClienteHttp clienteHttp;
    private final CalculadorPrioridad calculadorPrioridad;

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {

        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        String url = "https://www.carrefour.es/search-api/query/v1/search" +
                "?internal=true" +
                "&instance=x-carrefour" +
                "&env=https%3A%2F%2Fwww.carrefour.es" +
                "&scope=desktop" +
                "&lang=es" +
                "&session=empathy" +
                "&citrusCatalog=home" +
                "&baseUrlCitrus=https%3A%2F%2Fwww.carrefour.es" +
                "&enabled=false" +
                "&hasConsent=true" +
                "&raw=true" +
                "&catalog=food" +
                "&query=" + productoCodificado;

        //Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "es-ES,es;q=0.9");
        headers.put("referer", "https://www.carrefour.es/");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
        headers.put("priority", "u=1, i");
        headers.put("sec-ch-ua-platform", "\"Windows\"");


        // Usamos el CompletableFuture de manera asíncrona
        try {
            return clienteHttp.realizarPeticionHttp("GET", url, headers, null)
                    // Cuando termine de realizarse la petición convierte el json a lista
                    .thenApply(respuesta -> convertirJsonALista(respuesta))
                    // En el caso de que falle devuelve una lista vacía
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return new ArrayList<>(); // Si hay error, devuelve lista vacía
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductoDto> convertirJsonALista(String respuesta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RespuestaCarrefour respuestaMappeada = objectMapper.readValue(respuesta, RespuestaCarrefour.class);
            AtomicInteger index = new AtomicInteger(-1);

            return Optional.ofNullable(respuestaMappeada.getContent().getProductos())
                    .orElse(new ArrayList<>())
                    .stream()
                    .limit(10)
                    .map((Producto prod) -> mapearProducto(prod, index.getAndIncrement()))
                    .sorted(Comparator.comparing((ProductoDto prod) -> prod.getIndex()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ProductoDto mapearProducto(Producto producto, int index) {
            double precio = producto.getPrecio();
            double tamanoUnidad = producto.getTamanoUnidad();
            double precioGranel = precio / tamanoUnidad;

        return ProductoDto.builder()
                    .nombre(producto.getNombre())
                    .precio(precio)
                    .precioGranel(precioGranel)
                    .unidadMedida(producto.getUnidadMedida())
                    .tamanoUnidad(producto.getTamanoUnidad())
                    .prioridad(calculadorPrioridad.calcularSegunIndex(index))
                    .index(index)
                    .urlImagen(producto.getImagen().getUrlImagen())
                    .supermercado("CARREFOUR")
                    .build();


    }

}
