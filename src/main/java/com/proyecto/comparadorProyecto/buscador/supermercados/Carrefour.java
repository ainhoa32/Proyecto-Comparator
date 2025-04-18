package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Carrefour extends Peticion implements ObtenerProductos{

    @Override
    public List<List> obtenerListaSupermercado(String producto) {

        List<List> listaProductos = new ArrayList<>();
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        try {
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
                    "&shopperId=2urIV5VcxWjbeeSHoMozYI3pQG4" +
                    "&hasConsent=true" +
                    "&grid_def_search_sponsor_product=3%2C5%2C11%2C13%2C19" +
                    "&grid_def_search_butterfly_banner=7-8%2C15-16" +
                    "&grid_def_search_sponsor_product_tablet=2%2C4%2C11%2C13%2C19" +
                    "&grid_def_search_butterfly_banner_tablet=6%2C12" +
                    "&grid_def_search_sponsor_product_mobile=2%2C4%2C11%2C13%2C19" +
                    "&grid_def_search_butterfly_banner_mobile=6%2C12" +
                    "&grid_def_search_luckycart_banner=22" +
                    "&raw=true" +
                    "&catalog=food" +
                    "&query=almendra" + productoCodificado;

            //Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "*/*");
            headers.put("accept-language", "es-ES,es;q=0.9");
            headers.put("referer", "https://www.carrefour.es/");
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
            headers.put("priority", "u=1, i");
            headers.put("sec-ch-ua-platform", "\"Windows\"");

            String respuesta = realizarPeticionHttp("GET", url, headers, null);
            System.out.println(respuesta);
            listaProductos = convertirJsonALista(respuesta);

            System.out.println("--------------------CARREFOUR---------------------");
            System.out.println("Productos Carrefour: ");
            listaProductos.forEach(productoBuscado -> {
                System.out.println(productoBuscado);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    @Override
    public List<List> convertirJsonALista(String respuesta) {
        List<List> listaProductos = new ArrayList<>();

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
                List<Object> prod = List.of(nombre, precio, precioGranel, tamanoUnidad, unidadMedida, "CARREFOUR");
                listaProductos.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }
}
