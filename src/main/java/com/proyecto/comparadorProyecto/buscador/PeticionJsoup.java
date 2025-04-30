package com.proyecto.comparadorProyecto.buscador;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PeticionJsoup {

    public static CompletableFuture<Document> realizarPeticion(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
