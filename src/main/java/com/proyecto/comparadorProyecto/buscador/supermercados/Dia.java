package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.CalculadorPrioridad;
import com.proyecto.comparadorProyecto.buscador.ClienteHttp;
import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.models.dia.Producto;
import com.proyecto.comparadorProyecto.buscador.models.dia.RespuestaDia;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
// TODO: para hacerlo mas limpio es mejor hacerlo con jsoup ya que tiene un script embebido con el json con todos los datos que necesito bien clasificados
// TODO: OBLIGATORIO HACERLO YA QUE NO FUNCIONA CORRECTAMENTE EN CASOS MUY ESPECÍFICOS
public class Dia implements Supermercado {

    private final ClienteHttp clienteHttp;
    private final CalculadorPrioridad calculadorPrioridad;

    @Override
    public CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto) {

        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        String url = "https://www.dia.es/api/v1/search-insight/initial_analytics?q=" + productoCodificado + "&sort=rating,desc&page=1&page_size=30&filters=" +
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

        // Usamos el CompletableFuture de manera asíncrona
        try {
            return clienteHttp.realizarPeticionHttp("GET", url, headers, null)
                    // Cuando termine de realizarse la petición convierte el json a lista
                    .thenApply(respuesta -> convertirJsonALista(respuesta))
                    // En el caso de que falle devuelve una lista vacía
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return new ArrayList<>();
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductoDto> convertirJsonALista(String respuesta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RespuestaDia respuestaMappeada = objectMapper.readValue(respuesta, RespuestaDia.class);

            // Tenía esto:
            // Producto primerProducto = respuestaMappeada.getProductos().entrySet().stream().findFirst().get().getValue();
            // Pero no maneja nulos en el caso de que no exista el primer prod
            Producto primerProducto = Optional.ofNullable(respuestaMappeada.getProductos())
                    .orElse(Collections.emptyMap())
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getValue())
                    // Como los productos del día tienen un index
                    // y este está relacionado con la relevancia del producto
                    // respecto a la búsqueda unicamente el primer producto
                    .filter(producto -> producto.getIndex() == 1)
                    .findFirst()
                    .orElse(null);


            if (primerProducto == null) {
                return List.of();
            }

            List<String> categoriasPrioritarias = obtenerCategorias(primerProducto);

            return Optional.ofNullable(respuestaMappeada.getProductos())
                    .orElse(Collections.emptyMap()) // envía un mapa vacío si la respuesta es nula
                    .entrySet()// entry set obtiene las entradas del mapa Respuesta Dia
                    .stream()
                    // Filtramos los productos por index para que aparezcan los más relevantes obtenidos de la búsqueda
                    // Y así solo mapear los necesarios, es decir los 10 primeros
                    .filter(entry -> entry.getValue().getIndex() < 10)
                    .map(entry -> mapearProducto(entry, categoriasPrioritarias)) // Pasamos por parámetro el valor, es decir el producto de cada elemento del Map
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //Pasamos la clave valor del producto
    public ProductoDto mapearProducto(Map.Entry<String, Producto> productoEntry, List<String> categoriasPrioritarias) {
        String claveProducto = productoEntry.getKey();
        Producto valorProducto = productoEntry.getValue();
        int index = valorProducto.getIndex();
        int prioridad = calculadorPrioridad.calcularSegunIndex(index);
        String nombre = valorProducto.getNombre();
        double precio = valorProducto.getPrecio();

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

        if ((unidadMedida.equals("l") || unidadMedida.equals("kg")) && tamanoUnidad > 10) {
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
        BigDecimal precioGranel = new BigDecimal(precio / tamanoTotal).setScale(1, RoundingMode.HALF_UP);


        return ProductoDto.builder()
                .nombre(nombre)
                .precio(precio)
                .precioGranel(precioGranel.doubleValue())
                .index(index)
                .tamanoUnidad(tamanoUnidad)
                .unidadMedida(unidadMedida)
                .supermercado("DIA")
                .prioridad(prioridad)
                .urlImagen("https://www.dia.es/product_images/" + claveProducto + "/" + claveProducto + "_ISO_0_ES.jpg")
                .build();
    }

    private List<String> obtenerCategorias(Producto producto) {
        return List.of(
                producto.getCategoria1(),
                producto.getCategoria2()
        );
    }
}
