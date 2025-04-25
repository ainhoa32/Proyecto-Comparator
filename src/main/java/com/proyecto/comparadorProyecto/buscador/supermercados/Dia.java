package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.proyecto.comparadorProyecto.buscador.models.dia.Producto;
import com.proyecto.comparadorProyecto.buscador.models.dia.RespuestaDia;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Dia extends Peticion implements ObtenerProductos {

    @Override
    public List<ProductoDto> obtenerListaSupermercado(String producto) {

        List<ProductoDto> listaProductos = new ArrayList<>();
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

            String respuesta = realizarPeticionHttp("GET", url, headers, null);
            listaProductos = convertirJsonALista(respuesta);

//            System.out.println("--------------------Dia---------------------");
//            System.out.println("Productos Dia: ");
//            listaProductos.forEach(productoBuscado -> {
//                System.out.println(productoBuscado);
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    @Override
    public List<ProductoDto> convertirJsonALista(String respuesta) {
        List<ProductoDto> listaProductos = new ArrayList<>();

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            RespuestaDia respuestaMappeada = objectMapper.readValue(respuesta, RespuestaDia.class);

            if(respuestaMappeada.getProductos() != null) {
                for (Map.Entry<String, Producto> entry : respuestaMappeada.getProductos().entrySet()) {
                    Producto productoMappeado = entry.getValue(); // El objeto Producto asociado a esa clave

                    if(productoMappeado.getIndex() < 15) {
                        int index = productoMappeado.getIndex();
                        String nombre = productoMappeado.getNombre();
                        double precio = productoMappeado.getPrecio();
                        String categoriaPrioridad1 = productoMappeado.getCategoria1();
                        String categoriaPrioridad2 = productoMappeado.getCategoria2();
                        String unidadMedida;
                        double tamanoUnidad;
                        String [] producto = nombre.split(" ");

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

                        ProductoDto productoDto = ProductoDto.builder()
                                .nombre(nombre)
                                .precio(precio)
                                .precioGranel(precioGranel)
                                .index(index)
                                .tamanoUnidad(tamanoUnidad)
                                .unidadMedida(unidadMedida)
                                .supermercado("DIA")
                                .categoria1(categoriaPrioridad1)
                                .categoria2(categoriaPrioridad2)
                                .build();

                        listaProductos.add(productoDto);
                    }
                }
                // Ordenamos los productos por index para que estén ordenados por relevancia
                // Y que después al hacer el sublist no desaparezcan los de index más importante
                listaProductos.sort(Comparator.comparing(prod -> (int) prod.getIndex()));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos.subList(0, 10);
    }
}
