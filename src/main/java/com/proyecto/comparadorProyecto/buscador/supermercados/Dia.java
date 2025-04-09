package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dia extends Peticion implements ObtenerProductos {

    @Override
    public void hacerPeticionSupermercado(String producto) {
        try {
            String url = "https://www.dia.es/api/v1/search-insight/initial_analytics?q=almendras&sort=rating,desc&page=1&page_size=30&filters=";

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
            List<List> listaProductos = convertirJsonALista(respuesta);

            System.out.println("--------------------Dia---------------------");
            System.out.println("Productos Carrefour: ");
//            listaProductos.forEach(productoBuscado -> {
//                System.out.println(productoBuscado);
//            });

            System.out.println("Respuesta:");
//            System.out.println(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<List> convertirJsonALista(String responseStr) {
        return List.of();
    }
}
