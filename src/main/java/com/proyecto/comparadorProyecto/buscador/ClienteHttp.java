package com.proyecto.comparadorProyecto.buscador;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Peticion {

    public static CompletableFuture<String> realizarPeticionHttp(String metodo,
                                                                 String urlVisitar,
                                                                 Map<String, String> headers,
                                                                 String body) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(urlVisitar));

        if (headers != null) {
            headers.forEach(builder::header);
        }

        switch (metodo.toUpperCase()) {
            case "POST":
                if (body != null) {
                    builder.POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
                } else {
                    builder.POST(HttpRequest.BodyPublishers.noBody());
                }
                break;
            case "PUT":
                if (body != null) {
                    builder.PUT(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
                } else {
                    builder.PUT(HttpRequest.BodyPublishers.noBody());
                }
                break;
            case "DELETE":
                builder.DELETE();
                break;
            default: // GET y otros métodos sin body
                builder.GET();
                break;
        }

        HttpRequest request = builder.build();

        // Enviar petición asíncrona y devolver CompletableFuture
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
