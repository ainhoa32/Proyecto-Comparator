package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.ObtenerProductosScraping;
import com.proyecto.comparadorProyecto.buscador.PeticionJsoup;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class Ahorramas extends PeticionJsoup implements ObtenerProductosScraping {

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {
        String productoCodificado = URLEncoder.encode("leche", StandardCharsets.UTF_8);
        String url = "https://www.ahorramas.com/buscador?q=" + productoCodificado;
        return realizarPeticion(url)
                .thenApply(document -> convertirDocumentoALista(document))
                .exceptionally(e -> {
                    e.printStackTrace();
                    return new ArrayList<>();
                });
    }

    @Override
    public List<ProductoDto> convertirDocumentoALista(Document doc) {
        List<ProductoDto> productos = new ArrayList<>();
        return doc.select("div.product")
                .stream()
                .map(producto -> mapearProducto(producto))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto mapearProducto(Element producto) {
        String nombreProducto = producto.select("h2.product-name-gtm").text();
        int index = Integer.parseInt(producto.select("div.product-tile").attr("data-index"));
        Double precio = Double.parseDouble(producto.select("div.add-to-cart").attr("data-price"));
        String precioGranelString = producto.select("span.unit-price-per-unit").text();
        Double precioGranel = Double.parseDouble(precioGranelString.substring(1, precioGranelString.indexOf('€')).replace(",", "."));
        String unidadMedida = producto.select("div.cart-and-ipay").attr("data-frontunit");
        String urlImagen = producto.select("img.title-image").attr("src");
        String categoria2 = producto.select("div.product-tile").attr("data-category1");
        String categoria1 = producto.select("div.product-tile").attr("data-category2");

        double tamanoUnidad = 1*precio/precioGranel;

        return ProductoDto.builder()
                .nombre(nombreProducto)
                .supermercado("AHORRA MÁS")
                .unidadMedida(unidadMedida)
                .urlImagen(urlImagen)
                .categoria1(categoria1)
                .categoria2(categoria2)
                .index(index)
                .precioGranel(precioGranel)
                .precio(precio)
                .tamanoUnidad(tamanoUnidad)
                .build();
    }

}
