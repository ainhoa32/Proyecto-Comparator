package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.CalculadorPrioridad;
import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.PeticionJsoup;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
// TODO: Convertir PeticionJsoup a ClienteJsoup y ponerlo como atributo de clase
public class Ahorramas extends PeticionJsoup implements Supermercado {
    private final CalculadorPrioridad calculadorPrioridad;

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);
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
        Elements divProducto = doc.select("div.product");
        Element primerElemento = divProducto.first();

        if (primerElemento == null) {
            return List.of();
        }

        List<String> categoriasPrioritarias = obtenerCategorias(primerElemento);

        return divProducto.stream()
                .map(producto -> mapearProducto(producto, categoriasPrioritarias))
                .collect(Collectors.toList());
    }

    public ProductoDto mapearProducto(Element producto, List<String> categoriasPrioritarias) {
        String nombreProducto = producto.select("h2.product-name-gtm").text();
        int index = Integer.parseInt(producto.select("div.product-tile").attr("data-index"));
        Double precio = Double.parseDouble(producto.select("div.add-to-cart").attr("data-price"));
        String precioGranelString = producto.select("span.unit-price-per-unit").text();
        Double precioGranel = Double.parseDouble(precioGranelString.substring(1, precioGranelString.indexOf('€')).replace(",", "."));
        String unidadMedida = producto.select("div.add-to-cart.has-input").attr("data-frontunit");
        String urlImagen = producto.select("img.tile-image").attr("src");
        int prioridad = calculadorPrioridad.calcularSegunCategorias(obtenerCategorias(producto), categoriasPrioritarias);

        double tamanoUnidad = 1*precio/precioGranel;

        return ProductoDto.builder()
                .nombre(nombreProducto)
                .supermercado("AHORRA MÁS")
                .unidadMedida(unidadMedida)
                .urlImagen(urlImagen)
                .prioridad(prioridad)
                .index(index)
                .precioGranel(precioGranel)
                .precio(precio)
                .tamanoUnidad(tamanoUnidad)
                .build();
    }

    private List<String> obtenerCategorias(Element elemento) {
        return List.of(
                elemento.select("div.product-tile").attr("data-category1"),
                elemento.select("div.product-tile").attr("data-category2")
        );
    }
}
