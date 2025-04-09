package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mercadona extends Peticion implements ObtenerProductos {

    public Mercadona() {}

    @Override
    public void hacerPeticionSupermercado(String producto){
        //Codificamos el producto para poder incluirlo en la url
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        //Definimos la URL del servicio al que queremos hacer la petición
        String url = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_mad1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.35.1)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";

        // Definimos el cuerpo de la petición en formato json y recibimos la menor información posible
        String jsonBody = "{ \"params\": \"query=" + productoCodificado +
                "&attributesToRetrieve=display_name,price_instructions.unit_price,price_instructions.bulk_price,price_instructions.unit_size,price_instructions.size_format" + //atributo que quiero de cada objeto
                "&responseFields=hits\", " + //solo incliye el campo hits (donde se encuentran los resultados)
                "\"getRankingInfo\": false, " + //elimina información adicional
                "\"analytics\": false, " +
                "\"enableRules\": false, " +
                "\"clickAnalytics\": false, " +
                "\"attributesToHighlight\": [] }";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        try {
            // Llamamos a la función que realiza la solicitud HTTP POST y almacenamos la respuesta
            String respuesta = peticionHttpPost("POST", url, headers, jsonBody);
            List<List> listaProductos = convertirJsonALista(respuesta);

            System.out.println("--------------------MERCADONA---------------------");

            System.out.println(respuesta);
            listaProductos.forEach(productoBuscado -> {
                System.out.println(productoBuscado);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<List> convertirJsonALista(String responseStr) {

        List<List> productList = new ArrayList<>();

        try {
            // Usamos ObjectMapper para convertir el Json a un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            //readTree() convierte una json en un árbol de nodos
            //esto permite trabajar con el json de manera jerárquica.
            JsonNode rootNode = objectMapper.readTree(responseStr);

            if (rootNode.has("hits")) {
                JsonNode hitsArray = rootNode.get("hits");
                for (JsonNode productJson : hitsArray) {
                    //path no lanza excepciones si la clave no existe, en lugar de get
                    String nombre = productJson.path("display_name").asText();
                    double precio = productJson.path("price_instructions").path("unit_price").asDouble(0.0);
                    double precioGranel = productJson.path("price_instructions").path("bulk_price").asDouble(0.0);
                    double tamanoUnidad = productJson.path("price_instructions").path("unit_size").asDouble(0.0);
                    String formatoTamano = productJson.path("price_instructions").path("size_format").asText(null);
                    //Creamos una lista generica para incluir todos los campos del producto, este se inlcuirá en la lista que incluye
                    //a todos los elementos encontrados
                    List<Object> prod = List.of(nombre, precio, precioGranel, tamanoUnidad, formatoTamano);
                    productList.add(prod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

}
