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
            return false;
        }
        Usuario usuario = usuarioOpt.get();

        Cesta cesta = cestaRepository.findByUsuario(usuario).orElseGet(() -> {
            Cesta nuevaCesta = new Cesta();
            nuevaCesta.setUsuario(usuario);
            nuevaCesta.setProductos(new ArrayList<>());
            return nuevaCesta;
        });

        if (cesta.getProductos() != null && cesta.getProductos().size() >= 8) {
            return false;
        }

        Producto producto = new Producto();
        producto.setNombre(prod.getNombre());
        producto.setPrecio(BigDecimal.valueOf(prod.getPrecio()));
        producto.setPrecioGranel(BigDecimal.valueOf(prod.getPrecioGranel()));
        producto.setTamanoUnidad(BigDecimal.valueOf(prod.getTamanoUnidad()));
        producto.setUnidadMedida(prod.getUnidadMedida());
        producto.setSupermercado(prod.getSupermercado());
        producto.setPrioridad(prod.getPrioridad());
        producto.setFechaCreacion(LocalDateTime.now());

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


    public boolean eliminarProductoDeCesta(AgregarProductoCestaRequest request) {
        String nombreUsuario = request.getNombreUsuario();
        ProductoDto productoDto = request.getProducto();

        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByNombre(nombreUsuario));
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();

        Optional<Cesta> cestaOpt = cestaRepository.findByUsuario(usuario);
        if (cestaOpt.isEmpty()) {
            return false;
        }
        Cesta cesta = cestaOpt.get();

        List<Producto> productosEnCesta = cesta.getProductos();
        Producto productoAEliminar = null;

        for (Producto p : productosEnCesta) {
            if (p.getNombre().equals(productoDto.getNombre())) {
                productoAEliminar = p;
                break;
            }
        }

        if (productoAEliminar == null) {
            return false;
        }

        productosEnCesta.remove(productoAEliminar);

        cestaRepository.save(cesta);

        return true;
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
                        .build()
                )
                .collect(Collectors.toList());

        CestaDTO cestaDTO = new CestaDTO();
        cestaDTO.setId(cesta.getId());
        cestaDTO.setNombreUsuario(usuario.getNombre());
        cestaDTO.setProductos(productosDTO);

        return cestaDTO;
    }

    }