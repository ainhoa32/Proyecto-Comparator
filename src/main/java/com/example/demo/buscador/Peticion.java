package com.example.demo.buscador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Peticion {
    public static String peticionHttpPost(String urlVisitar, String jsonBody) throws Exception {
        URL url = new URL(urlVisitar);
        //Se abre conexión http con la url creada
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //permite el envío de datos en el cuerpo de la petición
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8"); // Convertimos el JSON a bytes con codificación UTF-8
            os.write(input, 0, input.length); // Escribimos los bytes en la conexión
        }
        // Creamos un StringBuilder para almacenar la respuesta del servidor
        StringBuilder resultado = new StringBuilder();

        // Abrimos un BufferedReader para leer la respuesta línea por línea
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String linea;
            while ((linea = rd.readLine()) != null) { // Mientras haya líneas disponibles
                resultado.append(linea.trim()); // Agregamos cada línea al StringBuilder (eliminando espacios extra)
            }
        }

        // Convertimos el StringBuilder a String y lo devolvemos como resultado de la función
        return resultado.toString();
    }
}
