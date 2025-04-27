package com.proyecto.comparadorProyecto.buscador.supermercados;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.comparadorProyecto.buscador.ObtenerProductos;
import com.proyecto.comparadorProyecto.buscador.Peticion;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// TODO: Arreglar Carrefour para que funcione en cualquier ordenador
@Component
public class Carrefour extends Peticion implements ObtenerProductos{

    @Override
    public List<ProductoDto> obtenerListaSupermercado(String producto) {

        try{
            String url = "https://www.carrefour.es/?query=almendras";

            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
            headers.put("accept-language", "es-ES,es;q=0.9,en-US;q=0.8,en;q=0.7,it;q=0.6");
            headers.put("cookie", "visid_incap_258278=mKkG3uwfShOnJErxGDn9O1aYx2YAAAAAQUIPAAAAAAAI7gN+NlUI4KWnOOXCUxfD; OptanonAlertBoxClosed=2025-03-22T12:26:11.532Z; eupubconsent-v2=CQOq1pgQOq1pgAcABBESBiFwAAAAAAAAAChQAAAAAAAA.YAAAAAAAAAAA; OneTrustGroupsConsent-ES=,C0097,C0003,C0001,; salepoint=005290||28232|A_DOMICILIO|1; session_id=2wJig3FlXcnTr5QCxScjffCgs6o; __cf_bm=XHW.UES1ti3CnGtOunF1TJoS9t9gQ42WX4BBmEaim9U-1745766184-1.0.1.1-2Md2mdQOfvn9ikAFi2igVTaKsR3iSDQmAVb_yejexby3boow8Dc68AGm7Eea.15NH71enKyxjgjpHMSh8i9v3ErOz5uwRixpMUyVdaDh6q0; cf_clearance=fMtEalB3NkQIYuCu1d0ZPfm3pYIEYWZ5RZCRCnWQQs0-1745766185-1.2.1.1-QwtLSMDoOmaUIeY7UynRZaLFAHIyIWeefli.97S.kP4hX45mLjPCL282v1l5Y3OrJHF4BfztZmtnndkXlKVZ8Sf0HOGTTd7hsKLkhhFH5i4uMIigLqVX312BNy.xtxHCQGRiAIt.d4Qn9_Ik7IkMw5t27zjc2bsPuw5IqIBvmK90AAA7DYn5QFXNwLRjhtljyfzMjWpZ8zzOS3kO.1tl9rzekn5aAwb6oViZoeqeUFNF1Vl3OBW3SBMITydiyDR2N5PJ.MUDBYwDZD04mOHekxATwdcqYYj5tN2LAhtiW0iWVtSgvpgqviKf7zod2wtFQ6E0qIT.wa6slh2uES77BOfT_NzWE2g553iaBwnjvDM; OptanonConsent=isGpcEnabled=0&datestamp=Sun+Apr+27+2025+17:09:36+GMT+0200+(hora+de+verano+de+Europa+central)&version=202410.1.0&browserGpcFlag=0&isIABGlobal=false&hosts=&consentId=e0f42598-eba7-4c7f-afa9-a0fae90b8138&interactionCount=1&isAnonUser=1&landingPath=NotLandingPage&groups=C0022:0,C0166:0,C0096:0,C0021:0,C0007:0,C0052:0,C0097:1,C0003:1,C0001:1,C0063:0,C0174:0,C0081:0,C0054:0,C0051:0,C0023:0,C0025:0,C0038:0,C0041:0,C0040:0,C0057:0,C0082:0,C0092:0,C0135:0,C0141:0,C0180:0,C0084:0,C0167:0,C0032:0,C0039:0,C0004:0,V2STACK42:0&intType=2&geolocation=ES;MD&AwaitingReconsent=false");
            headers.put("priority", "u=0, i");
            headers.put("sec-ch-ua", "\"Google Chrome\";v=\"135\", \"Not-A.Brand\";v=\"8\", \"Chromium\";v=\"135\"");
            headers.put("sec-ch-ua-mobile", "?0");
            headers.put("sec-ch-ua-platform", "\"Windows\"");
            headers.put("sec-fetch-dest", "document");
            headers.put("sec-fetch-mode", "navigate");
            headers.put("sec-fetch-site", "none");
            headers.put("sec-fetch-user", "?1");
            headers.put("upgrade-insecure-requests", "1");
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36");

            String respuesta = realizarPeticionHttp("GET", url, headers, null);

            System.out.println(respuesta);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        List<ProductoDto> listaProductos = new ArrayList<>();
        String productoCodificado = URLEncoder.encode(producto, StandardCharsets.UTF_8);

        try {
            String url = "https://www.carrefour.es/search-api/query/v1/search" +
                    "?internal=true" +
                    "&instance=x-carrefour" +
                    "&env=https%3A%2F%2Fwww.carrefour.es" +
                    "&scope=desktop" +
                    "&lang=es" +
                    "&session=empathy" +
                    "&citrusCatalog=home" +
                    "&baseUrlCitrus=https%3A%2F%2Fwww.carrefour.es" +
                    "&enabled=false" +
                    "&hasConsent=true" +
                    "&raw=true" +
                    "&catalog=food" +
                    "&query=" + productoCodificado;

            //Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "*/*");
            headers.put("accept-language", "es-ES,es;q=0.9");
            headers.put("referer", "https://www.carrefour.es/");
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
            headers.put("priority", "u=1, i");
            headers.put("sec-ch-ua-platform", "\"Windows\"");

            String respuesta = realizarPeticionHttp("GET", url, headers, null);
            listaProductos = convertirJsonALista(respuesta);

//            System.out.println("--------------------CARREFOUR---------------------");
//            System.out.println("Productos Carrefour: ");
//            listaProductos.forEach(productoBuscado -> {
//                System.out.println(productoBuscado);
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    // TODO: Hay que eliminar el uso del read tree y sustituirlo con modelos para mappear el JSON
    @Override
    public List<ProductoDto> convertirJsonALista(String respuesta) {
        List<ProductoDto> listaProductos = new ArrayList<>();

        try {
            //Convertimos el Json en un JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            //readTree() convierte una json en un árbol de nodos
            //esto permite trabajar con el json de manera jerárquica.
            JsonNode rootNode = objectMapper.readTree(respuesta);

            JsonNode docs = rootNode.path("content").path("docs");
            for (JsonNode producto : docs) {
                String nombre = producto.path("display_name").asText();
                double precio = producto.path("active_price").asDouble(0.0);
                double tamanoUnidad = producto.path("unit_conversion_factor").asDouble(0.0);
                String unidadMedida = producto.path("measure_unit").asText();
                double precioGranel = precio / tamanoUnidad;
                //Creamos una lista generica para incluir todos los campos del producto, este se inlcuirá en la lista que incluye
                //a todos los elementos encontrados
                ProductoDto productoDto = new ProductoDto(nombre, precio, precioGranel, tamanoUnidad, unidadMedida, 0, "categoría", "categoria2","CARREFOUR");

                listaProductos.add(productoDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos.subList(0, 10);
    }
}
