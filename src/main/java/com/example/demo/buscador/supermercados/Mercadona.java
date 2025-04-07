package com.example.demo.buscador.supermercados;

import com.example.demo.buscador.Peticion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Mercadona extends Peticion {

    public Mercadona() {}

    public static void comparar(){
        // Definimos la URL del servicio al que queremos hacer la petición
        String url = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_mad1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.35.1)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";

        // Definimos el cuerpo de la petición en formato JSON y recibimos la menor información posible
        String jsonBody = "{ \"params\": \"query=leche%20entera" +
                "&attributesToRetrieve=display_name,price_instructions.unit_price,price_instructions.bulk_price" + //atributo que quiero de cada objeto
                "&responseFields=hits\", " + //solo incliye el campo hits (donde se encuentran los resultados)
                "\"getRankingInfo\": false, " + //elimina información adicional
                "\"analytics\": false, " +
                "\"enableRules\": false, " +
                "\"clickAnalytics\": false, " +
                "\"attributesToHighlight\": [] }";

                try {
            // Llamamos a la función que realiza la solicitud HTTP POST y almacenamos la respuesta
            String respuesta = peticionHttpPost(url, jsonBody);

            List<String> products = getProductList(respuesta);
            products.forEach(System.out::println);
        } catch (Exception e) {
            // Capturamos cualquier excepción y mostramos el error
            e.printStackTrace();
        }
    }

    private static List<String> getProductList(String responseStr) {
        System.out.println(responseStr);
        List<String> productList = new ArrayList<>();
        try {

            // Usamos ObjectMapper para convertir el JSON a un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            //JSON limpio en un JsonNode. readTree() es el metodo que convierte una cadena JSON en un árbol
            //de nodos (lo que permite trabajar con el JSON de manera jerárquica).
            JsonNode rootNode = objectMapper.readTree(responseStr);

            if (rootNode.has("hits")) {
                JsonNode hitsArray = rootNode.get("hits");
                for (JsonNode productJson : hitsArray) {
                    //path no lanza excepciones si la clave no existe, en lugar de get
                    String nombre = productJson.path("display_name").asText();
                    double precio = productJson.path("price_instructions").path("unit_price").asDouble(0.0);
                    double precioGranel = productJson.path("price_instructions").path("bulk_price").asDouble(0.0);
                    productList.add("Producto: " + nombre + ", Precio: " + precio + ", Precio Kilo/litro: " + precioGranel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

}
