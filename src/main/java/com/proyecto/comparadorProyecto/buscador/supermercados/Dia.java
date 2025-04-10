package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dia extends Peticion implements ObtenerProductos {

    @Override
    public List<List> obtenerListaSupermercado(String producto) {

        List<List> listaProductos = new ArrayList<>();
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        try {
            String url = "https://www.dia.es/api/v1/search-insight/initial_analytics?q=" + productoCodificado +"&sort=rating,desc&page=1&page_size=30&filters=" +
                    "&attributesToRetrieve=search_products_analytics";

            //Headers
            Map<String, String> headers = new HashMap<>();

            headers.put("accept", "application/json");
            headers.put("accept-language", "es-ES,es;q=0.9");
            headers.put("cart_id", "21a31654-54ad-46ee-9c55-2488115a118a");
            headers.put("customer_id", "");
            headers.put("priority", "u=1, i");
            headers.put("referer", "https://www.dia.es/search?q=almendras");
            headers.put("sec-ch-ua", "\"Google Chrome\";v=\"135\", \"Not-A.Brand\";v=\"8\", \"Chromium\";v=\"135\"");
            headers.put("sec-ch-ua-mobile", "?0");
            headers.put("sec-ch-ua-platform", "\"Windows\"");
            headers.put("sec-fetch-dest", "empty");
            headers.put("sec-fetch-mode", "cors");
            headers.put("sec-fetch-site", "same-origin");
            headers.put("session_id", "3d9d40b1-51d7-41cb-a5a9-d60752f09760");
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36");
            headers.put("x-locale", "es");
            headers.put("x-mobile", "");
            headers.put("x-requested-with", "XMLHttpRequest");

            String respuesta = peticionHttpPost("GET", url, headers, null);
            listaProductos = convertirJsonALista(respuesta);

            System.out.println("--------------------Dia---------------------");
            System.out.println("Productos Dia: ");
            listaProductos.forEach(productoBuscado -> {
                System.out.println(productoBuscado);
            });

//            System.out.println("Respuesta:");
//            System.out.println(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    @Override
    public List<List> convertirJsonALista(String responseStr) {
        List<List> listaProductos = new ArrayList<>();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseStr);

            JsonNode productoBuscado = jsonNode.path("search_products_analytics");
            for(JsonNode productoJson : productoBuscado) {
                if(productoJson.path("index").asInt() < 15) {
                    int index = productoJson.path("index").asInt();
                    String nombre = productoJson.path("item_name").asText();
                    double precio = productoJson.path("price").asDouble(0.0);
                    String unidadMedida;
                    double tamanoUnidad;
                    String[] producto = nombre.split(" ");

                    if (producto[producto.length - 1].equals("aprox")) {
                        unidadMedida = producto[producto.length - 2];
                        tamanoUnidad = Double.parseDouble(producto[producto.length - 3]);
                    } else {
                        unidadMedida = producto[producto.length - 1];
                        tamanoUnidad = Double.parseDouble(producto[producto.length - 2]);
                    }

                    //Hay un problema con como devuelve la api del Dia las bebidas, en vez de poner
                    //2.5 litros, aparecen como 25 litros, como no consta que haya líquidos de más
                    //de 10 litros, si aparece algún líquido mayor es porque es un líquido cuyo formato
                    //no es correcto como he puesto antes, si aparecen 25 litros, obviamente no serán 25 litros
                    //serán 2.5 así que lo divido entre 10 para que aparezca correctamente
                    if (unidadMedida.equals("l") && tamanoUnidad > 10) {
                        tamanoUnidad = tamanoUnidad / 10;
                    }

                    if (unidadMedida.equals("ml")) {
                        tamanoUnidad = tamanoUnidad / 1000;
                        unidadMedida = "l";
                    }

                    if (unidadMedida.equals("g")) {
                        tamanoUnidad = tamanoUnidad / 1000;
                        unidadMedida = "kg";
                    }

                    double tamanoTotal = tamanoUnidad;
                    if (producto[producto.length - 3].equals("x")) {
                        tamanoTotal = tamanoUnidad * Double.parseDouble(producto[producto.length - 4]);
                    }

                    double precioGranel = precio / tamanoTotal;


                    List<Object> prod = List.of(nombre, precio, precioGranel, tamanoUnidad, unidadMedida, index, "DIA");
                    listaProductos.add(prod);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos;
    }
}
