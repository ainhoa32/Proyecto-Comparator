package com.proyecto.comparadorProyecto.buscador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Peticion {

    public static String realizarPeticionHttp(String metodo, String urlVisitar, Map<String, String> headers, String body   ) throws Exception {

        URL url = new URL(urlVisitar);
        //Se abre conexión http con la url creada
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(metodo.toUpperCase());


        // Headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }


        // POST body
        if ("POST".equalsIgnoreCase(metodo) && body != null) {
            //permite el envío de datos en el cuerpo de la petición
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        // Leer respuesta
        int responseCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String linea;
        while ((linea = in.readLine()) != null) {
            response.append(linea.trim());
        }
        in.close();

        return response.toString();
    }
}
