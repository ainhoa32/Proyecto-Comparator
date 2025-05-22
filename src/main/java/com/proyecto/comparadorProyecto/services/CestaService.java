package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.AgregarProductoCestaRequest;
import com.proyecto.comparadorProyecto.dto.CestaDTO;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.models.Producto;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.CestaRepository;
import com.proyecto.comparadorProyecto.repository.ProductoRepository;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CestaService {

    @Autowired
    private CestaRepository cestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public boolean agregarProductoACesta(AgregarProductoCestaRequest request) {
        String user = request.getNombreUsuario();
        ProductoDto prod = request.getProducto();

        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByNombre(user));
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        Usuario usuario = usuarioOpt.get();

        Cesta cesta = cestaRepository.findByUsuario(usuario).orElseGet(() -> {
            Cesta nuevaCesta = new Cesta();
            nuevaCesta.setUsuario(usuario);
            nuevaCesta.setProductos(new ArrayList<>());
            return nuevaCesta;
        });

        if (cesta.getProductos() != null && cesta.getProductos().size() >= 50) {
            throw new IllegalStateException("La cesta ya contiene el máximo de 50 productos.");
        }

        Producto producto = new Producto();
        producto.setNombre(prod.getNombre());
        producto.setPrecio(BigDecimal.valueOf(prod.getPrecio()));
        producto.setPrecioGranel(BigDecimal.valueOf(prod.getPrecioGranel()));
        producto.setTamanoUnidad(BigDecimal.valueOf(prod.getTamanoUnidad()));
        producto.setUrlImagen(prod.getUrlImagen());
        producto.setUnidadMedida(prod.getUnidadMedida());
        producto.setSupermercado(prod.getSupermercado());
        producto.setPrioridad(prod.getPrioridad());
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setIndice(prod.getIndex());

        producto = productoRepository.save(producto);

        if (cesta.getProductos() == null) {
            cesta.setProductos(new ArrayList<>());
        }

        Producto finalProducto = producto;
        if (cesta.getProductos().stream().noneMatch(p -> p.getId().equals(finalProducto.getId()))) {
            cesta.getProductos().add(producto);
        }

        cestaRepository.save(cesta);
        return true;
    }

    public void eliminarCesta(String user) {

        System.out.println("eliminando cesta " + user);
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByNombre(user))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Cesta cesta = cestaRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("La cesta del usuario está vacía."));

        List<Producto> productosEnCesta = new ArrayList<>(cesta.getProductos());

        cesta.setProductos(new ArrayList<>());
        cestaRepository.save(cesta);

        productoRepository.deleteAll(productosEnCesta);
    }


    public void eliminarProductoDeCesta(AgregarProductoCestaRequest request) {
        String nombreUsuario = request.getNombreUsuario();
        ProductoDto productoDto = request.getProducto();

        Usuario usuario = Optional.ofNullable(usuarioRepository.findByNombre(nombreUsuario))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Cesta cesta = cestaRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("La cesta del usuario esta vacia."));

        List<Producto> productosEnCesta = cesta.getProductos();
        Producto productoAEliminar = productosEnCesta.stream()
                .filter(p -> p.getNombre().equals(productoDto.getNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("El producto no está en la cesta."));

        productosEnCesta.remove(productoAEliminar);
        cestaRepository.save(cesta);

        productoRepository.delete(productoAEliminar);
    }


    public CestaDTO obtenerCestaPorUsuario(String nombreUsuario) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByNombre(nombreUsuario));
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        Optional<Cesta> cestaOpt = cestaRepository.findByUsuario(usuario);
        if (cestaOpt.isEmpty()) {
            return null;
        }
        Cesta cesta = cestaOpt.get();

        List<ProductoDto> productosDTO = cesta.getProductos().stream()
                .map(p -> ProductoDto.builder()
                        .nombre(p.getNombre())
                        .precio(p.getPrecio().doubleValue())
                        .unidadMedida(p.getUnidadMedida())
                        .supermercado(p.getSupermercado())
                        .precioGranel(p.getPrecioGranel().doubleValue())
                        .urlImagen(p.getUrlImagen())
                        .tamanoUnidad(p.getTamanoUnidad().doubleValue())
                        .index(p.getIndice())
                        .prioridad(p.getPrioridad())
                        .build()
                )
                .collect(Collectors.toList());

        CestaDTO cestaDTO = new CestaDTO();
        cestaDTO.setId(cesta.getId());
        cestaDTO.setNombreUsuario(usuario.getNombre());
        cestaDTO.setProductos(productosDTO);

        return cestaDTO;
    }
    public CestaDTO crearCestaUserSiNoTiene(String nombreUsuario) {
        CestaDTO vacia = new CestaDTO();
        vacia.setNombreUsuario(nombreUsuario);
        vacia.setProductos(new ArrayList<>());
        return vacia;
    }

    }