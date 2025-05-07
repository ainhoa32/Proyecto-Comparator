package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.CalculadorPrioridad;
import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.Producto;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.PriceInstructions;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.RespuestaMercadona;
import com.proyecto.comparadorProyecto.buscador.ClienteHttp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mercadona implements Supermercado {

    private final ClienteHttp clienteHttp;
    private final CalculadorPrioridad calculadorPrioridad;

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {
        // Codificamos el producto para poder incluirlo en la URL
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        // Definimos la URL del servicio al que queremos hacer la petición
        String url = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_mad1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.35.1)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";

        // Definimos el cuerpo de la petición en formato json y recibimos la menor información posible
        String jsonBody = "{ \"params\": \"query=" + productoCodificado +
                "&attributesToRetrieve=categories,display_name,thumbnail,price_instructions.unit_price," +
                "price_instructions.bulk_price,price_instructions.unit_size,price_instructions.size_format" +
                "&responseFields=hits\", " +
                "\"getRankingInfo\": false, " +
                "\"analytics\": false, " +
                "\"enableRules\": false, " +
                "\"clickAnalytics\": false, " +
                "\"attributesToHighlight\": [] }";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        // Usamos el CompletableFuture de manera asíncrona
        try {
            return clienteHttp.realizarPeticionHttp("POST", url, headers, jsonBody)
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

        List<ProductoDto> listaProductos = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            RespuestaMercadona respuestMappeada = objectMapper.readValue(respuesta, RespuestaMercadona.class);

            Producto primerProducto = Optional.ofNullable(respuestMappeada.getProducto())
                    .orElse(List.of())
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (primerProducto == null) {
                return List.of();
            }

            List<String> categoriasPrioritarias = obtenerCategorias(primerProducto);

            AtomicInteger index = new AtomicInteger(1);

            return Optional.ofNullable(respuestMappeada.getProducto())
                    .orElse(List.of())
                    .stream()
                    // Solo vamos a manejar los primeros 10 productos que nos devuelve el json
                    // de los productos del mercadona ya que son los más relevantre
                    .limit(10)
                    .map(prod -> mapearProducto(prod, index.getAndIncrement(), categoriasPrioritarias))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos.size() >= 10 ? listaProductos.subList(0, 10) : listaProductos;
    }

    public ProductoDto mapearProducto(Producto producto, int index, List<String> categoriasPrioritarias) {
        PriceInstructions preciosProducto = producto.getPriceInstructions();
        //int prioridad = calculadorPrioridad.calcularSegunCategorias(obtenerCategorias(producto), categoriasPrioritarias);
        int prioridad = calculadorPrioridad.calcularSegunIndex(index);

        return ProductoDto.builder()
                .nombre(producto.getNombre())
                .precio(preciosProducto.getPrecioUnidad())
                .precioGranel(preciosProducto.getPrecioGranel())
                .unidadMedida(preciosProducto.getUnidadMedida())
                .tamanoUnidad(preciosProducto.getTamanoUnidad())
                .index(index)
                .prioridad(prioridad)
                .urlImagen(producto.getUrlImagen())
                .supermercado("MERCADONA")
                .build();
    }

    private List<String> obtenerCategorias(Producto producto) {
        return List.of(
                producto.getCategoria().getFirst().getCategoria().getFirst().getNombreCategoria(),
                producto.getCategoria().getFirst().getNombreCategoria()
        );
    }

}
