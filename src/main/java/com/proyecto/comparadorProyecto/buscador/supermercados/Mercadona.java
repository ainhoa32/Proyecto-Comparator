package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.models.mercadona.Hit;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.PriceInstructions;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.RespuestaMercadona;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@NoArgsConstructor
public class Mercadona extends Peticion implements ObtenerProductos {

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {
        // Codificamos el producto para poder incluirlo en la URL
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        // Definimos la URL del servicio al que queremos hacer la petición
        String url = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_mad1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.35.1)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";

        // Definimos el cuerpo de la petición en formato json y recibimos la menor información posible
        String jsonBody = "{ \"params\": \"query=" + productoCodificado +
                "&attributesToRetrieve=categories,display_name,price_instructions.unit_price," +
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
            return realizarPeticionHttp("POST", url, headers, jsonBody)
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


    @Override
    public List<ProductoDto> convertirJsonALista(String respuesta) {

        List<ProductoDto> listaProductos = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            RespuestaMercadona respuestMappeada = objectMapper.readValue(respuesta, RespuestaMercadona.class);

            int index = 1;

            if(respuestMappeada.getHits().size() > 0) {
                for (Hit producto : respuestMappeada.getHits()) {
                    PriceInstructions preciosProducto = producto.getPriceInstructions();

                    String categoriaPrioridad1 = producto.getCategoria().getFirst().getCategoria().getFirst().getNombreCategoria();
                    String categoriaPrioridad2 = producto.getCategoria().getFirst().getNombreCategoria();

                    //Creamos una lista generica para incluir todos los campos del producto, este se inlcuirá en la lista que incluye
                    //a todos los elementos encontrados

                    ProductoDto productoDto = ProductoDto.builder()
                            .nombre(producto.getNombre())
                            .precio(preciosProducto.getPrecioUnidad())
                            .precioGranel(preciosProducto.getPrecioGranel())
                            .unidadMedida(preciosProducto.getUnidadMedida())
                            .tamanoUnidad(preciosProducto.getTamanoUnidad())
                            .index(index++)
                            .categoria1(categoriaPrioridad1)
                            .categoria2(categoriaPrioridad2)
                            .urlImagen(producto.getUrlImagen())
                            .supermercado("MERCADONA")
                            .build();

                    listaProductos.add(productoDto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos.size() >= 10 ? listaProductos.subList(0, 10) : listaProductos;
    }

}
