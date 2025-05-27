package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.AgregarProductoAListaDTO;
import com.proyecto.comparadorProyecto.dto.ListaPredeterminadaDTO;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import com.proyecto.comparadorProyecto.models.ListaProducto;
import com.proyecto.comparadorProyecto.models.ListaProductoId;
import com.proyecto.comparadorProyecto.models.ListasPredeterminada;
import com.proyecto.comparadorProyecto.models.Producto;
import com.proyecto.comparadorProyecto.repository.ListaPredeterminadaRepository;
import com.proyecto.comparadorProyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListaPredeterminadaService {

    @Autowired
    private ListaPredeterminadaRepository listaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public Optional<ListaPredeterminadaDTO> obtenerListaPorNombre(String nombre) {
        return listaRepo.findByNombre(nombre)
                .map(this::convertirADTO);
    }

    public void agregarProductoALista(AgregarProductoAListaDTO dto) {
        Producto producto = productoRepo.findByNombre(dto.getProducto().getNombre())
                .orElseGet(() -> {
                    Producto nuevo = new Producto();
                    agregarDatosAProducto(dto.getProducto(), nuevo);
                    return productoRepo.save(nuevo);  // Persistimos el nuevo producto
                });

        ListasPredeterminada lista = listaRepo.findByNombre(dto.getNombre())
                .orElseThrow(() -> new RuntimeException("Lista no encontrada: " + dto.getNombre()));

        ListaProductoId id = new ListaProductoId();
        id.setProductoId(producto.getId());
        id.setListaId(lista.getId());

        boolean yaExiste = lista.getListaProductos().stream()
                .anyMatch(lp -> lp.getId().equals(id));
        if (yaExiste) {
            throw new RuntimeException("El producto ya está en la lista.");
        }

        ListaProducto lp = new ListaProducto();
        lp.setId(id);
        lp.setProducto(producto);
        lp.setLista(lista);

        lista.getListaProductos().add(lp);
        listaRepo.save(lista);
    }

    public List<ListasPredeterminada> obtenerTodasLasListas() {
        return (List<ListasPredeterminada>) listaRepo.findAll();
    }

    public List<ListasPredeterminada> obtenerListasVisibles() {
        return listaRepo.findByEsVisible(true);
    }

    public void alternarVisibilidadPorNombre(String nombre) {
        ListasPredeterminada lista = listaRepo.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada con nombre: " + nombre));

        boolean visibilidadActual = lista.getEsVisible();
        lista.setEsVisible(visibilidadActual == true ? false : true);
        listaRepo.save(lista);
    }
    private Producto agregarDatosAProducto(ProductoDto p, Producto nuevo){
        Producto prod= new Producto();
        prod.setNombre(p.getNombre());
        prod.setPrecio(BigDecimal.valueOf(p.getPrecio()));
        prod.setPrecioGranel(BigDecimal.valueOf(p.getPrecioGranel()));
        if (p.getTamanoUnidad() != null) {
            prod.setTamanoUnidad(BigDecimal.valueOf(p.getTamanoUnidad()));
        }
        prod.setUnidadMedida(p.getUnidadMedida());
        if (p.getIndex() != 0) {
            prod.setIndice(p.getIndex());
        }
        prod.setUrlImagen(p.getUrlImagen());
        if (p.getPrioridad() != 0) {
            prod.setPrioridad(p.getPrioridad());
        }
        prod.setSupermercado(p.getSupermercado());
        return prod;
    }



    private ListaPredeterminadaDTO convertirADTO(ListasPredeterminada lista) {
        List<ProductoDto> productosDto = lista.getListaProductos().stream()
                .map(lp -> {
                    Producto p = lp.getProducto();
                    return ProductoDto.builder()
                            .nombre(p.getNombre())
                            .precio(p.getPrecio().doubleValue())
                            .precioGranel(p.getPrecioGranel().doubleValue())
                            .tamanoUnidad(p.getTamanoUnidad() != null ? p.getTamanoUnidad().doubleValue() : null)
                            .unidadMedida(p.getUnidadMedida())
                            .index(p.getIndice() != null ? p.getIndice() : 0)
                            .urlImagen(p.getUrlImagen())
                            .prioridad(p.getPrioridad())
                            .supermercado(p.getSupermercado())
                            .build();
                }).collect(Collectors.toList());

        return ListaPredeterminadaDTO.builder()
                .nombre(lista.getNombre())
                .productos(productosDto)
                .build();
    }

    public void crearLista(String nombreLista) {
        if (listaRepo.findByNombre(nombreLista).isPresent()) {
            throw new RuntimeException("Ya existe una lista con ese nombre: " + nombreLista);
        }
        ListasPredeterminada nuevaLista = new ListasPredeterminada();
        nuevaLista.setNombre(nombreLista);
        listaRepo.save(nuevaLista);
    }
}

