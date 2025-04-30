package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.proyecto.comparadorProyecto.buscador.models.carrefour.Producto;
import com.proyecto.comparadorProyecto.buscador.models.carrefour.RespuestaCarrefour;
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
            ObjectMapper objectMapper = new ObjectMapper();
            RespuestaCarrefour respuestaMappeada = objectMapper.readValue(respuesta, RespuestaCarrefour.class);

            if(respuestaMappeada.getContent().getProductos().size() > 0){
                int index = -1;
                for(Producto producto : respuestaMappeada.getContent().getProductos()){
                    double precio = producto.getPrecio();
                    double tamanoUnidad = producto.getTamanoUnidad();
                    double precioGranel =  precio / tamanoUnidad;

                    ProductoDto productoDto = ProductoDto.builder()
                            .nombre(producto.getNombre())
                            .precio(precio)
                            .precioGranel(precioGranel)
                            .unidadMedida(producto.getUnidadMedida())
                            .tamanoUnidad(producto.getTamanoUnidad())
                            .index(index++)
                            .categoria1(null)
                            .categoria2(null)
                            .urlImagen(producto.getImagen().getUrlImagen())
                            .supermercado("MERCADONA")
                            .build();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos.size() >= 10 ? listaProductos.subList(0, 10) : listaProductos;
    }
}
