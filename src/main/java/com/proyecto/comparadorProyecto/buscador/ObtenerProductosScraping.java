package com.proyecto.comparadorProyecto.buscador;

import com.proyecto.comparadorProyecto.dto.ProductoDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ObtenerProductosScraping {

    CompletableFuture<List<ProductoDto>> obtenerListaSupermercado(String producto);

    public List<ProductoDto> convertirDocumentoALista(Document doc);

    public ProductoDto mapearProducto(Element producto);
}
