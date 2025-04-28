package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


// TODO: Arreglar Carrefour para que funcione en cualquier ordenador
@Component
public class Carrefour extends Peticion implements ObtenerProductos{

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {

        List<ProductoDto> listaProductos = new ArrayList<>();
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
                return realizarPeticionHttp("GET", url, headers, null)
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

    // TODO: Hay que eliminar el uso del read tree y sustituirlo con modelos para mappear el JSON
    @Override
    public List<ProductoDto> convertirJsonALista(String respuesta) {
        List<ProductoDto> listaProductos = new ArrayList<>();

        try {
            //Convertimos el Json en un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            //readTree() convierte una json en un árbol de nodos
            //esto permite trabajar con el json de manera jerárquica.
            JsonNode rootNode = objectMapper.readTree(respuesta);

            JsonNode docs = rootNode.path("content").path("docs");
            for (JsonNode producto : docs) {
                String nombre = producto.path("display_name").asText();
                double precio = producto.path("active_price").asDouble(0.0);
                double tamanoUnidad = producto.path("unit_conversion_factor").asDouble(0.0);
                String unidadMedida = producto.path("measure_unit").asText();
                double precioGranel = precio / tamanoUnidad;
                //Creamos una lista generica para incluir todos los campos del producto, este se inlcuirá en la lista que incluye
                //a todos los elementos encontrados
                ProductoDto productoDto = new ProductoDto(nombre, precio, precioGranel, tamanoUnidad, unidadMedida, 0, "categoría", "categoria2","CARREFOUR");

                listaProductos.add(productoDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos.size() > 0 ? listaProductos.subList(0, 10) : listaProductos;
    }
}
