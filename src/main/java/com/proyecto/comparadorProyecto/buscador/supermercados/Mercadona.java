package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.proyecto.comparadorProyecto.buscador.models.mercadona.Hit;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.NombreCategoria;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.PriceInstructions;
import com.proyecto.comparadorProyecto.buscador.models.mercadona.RespuestaMercadona;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class Mercadona extends Peticion implements ObtenerProductos {

    @Override
    public List<List> obtenerListaSupermercado(String producto){
        List<List> listaProductos = new ArrayList<>();
        //Codificamos el producto para poder incluirlo en la url
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        //Definimos la URL del servicio al que queremos hacer la petición
        String url = "https://7uzjkl1dj0-dsn.algolia.net/1/indexes/products_prod_mad1_es/query?x-algolia-agent=Algolia%20for%20JavaScript%20(3.35.1)%3B%20Browser&x-algolia-application-id=7UZJKL1DJ0&x-algolia-api-key=9d8f2e39e90df472b4f2e559a116fe17";

        // Definimos el cuerpo de la petición en formato json y recibimos la menor información posible
        String jsonBody = "{ \"params\": \"query=" + productoCodificado +
                "&attributesToRetrieve=display_name,price_instructions.unit_price,price_instructions.bulk_price,price_instructions.unit_size,price_instructions.size_format" + //atributo que quiero de cada objeto
                "&responseFields=hits\", " + //solo incliye el campo hits (donde se encuentran los resultados)
                "\"getRankingInfo\": false, " + //elimina información adicional
                "\"analytics\": false, " +
                "\"enableRules\": false, " +
                "\"clickAnalytics\": false, " +
                "\"attributesToHighlight\": [] }";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        try {
            // Llamamos a la función que realiza la solicitud HTTP POST y almacenamos la respuesta
            String respuesta = realizarPeticionHttp("POST", url, headers, jsonBody);
            listaProductos = convertirJsonALista(respuesta);

            System.out.println("--------------------MERCADONA---------------------");

            listaProductos.forEach(productoBuscado -> {
                System.out.println(productoBuscado);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    @Override
    public List<List> convertirJsonALista(String respuesta) {

        List<List> productList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            RespuestaMercadona respuestMappeada = objectMapper.readValue(respuesta, RespuestaMercadona.class);

                for (Hit producto : respuestMappeada.getHits()) {
                    PriceInstructions preciosProducto = producto.getPriceInstructions();

                    // TODO: Hacer que se puedan obtener las categorias de los productos, por ahora aparecen como null
//                    NombreCategoria nombreCategoria = producto.getCategoria().get(0).getNombreCategoria();

                    System.out.println(producto.getCategoria());

                    //Creamos una lista generica para incluir todos los campos del producto, este se inlcuirá en la lista que incluye
                    //a todos los elementos encontrados
                    List<Object> prod = List.of(producto.getNombre(), preciosProducto.getPrecioUnidad(), preciosProducto.getPrecioGranel(),
                            preciosProducto.getTamanoUnidad(), preciosProducto.getUnidadMedida(), "MERCADONA");
                    productList.add(prod);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

}
