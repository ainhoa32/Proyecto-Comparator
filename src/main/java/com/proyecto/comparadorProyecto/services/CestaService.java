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

    public boolean agregarProductoACesta(AgregarProductoCestaRequest request){
        String user = request.getNombreUsuario();
        ProductoDto prod = request.getProducto();

        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByNombre(user));
        if(usuario.isEmpty()){
            return false;
        }
        Usuario u = usuario.get();
        Producto p = new Producto();
        p.setNombre(prod.getNombre());
        p.setPrecio(BigDecimal.valueOf(prod.getPrecio()));
        p.setPrecioGranel(BigDecimal.valueOf(prod.getPrecioGranel()));
        p.setTamanoUnidad(BigDecimal.valueOf(prod.getTamanoUnidad()));
        p.setUnidadMedida(prod.getUnidadMedida());
        p.setSupermercado(prod.getSupermercado());
        p.setPrioridad(0);
        p.setFechaCreacion(LocalDateTime.now());

        p=productoRepository.save(p);

        Cesta cesta = cestaRepository.findByUsuario(u).orElseGet(() -> {
            Cesta nueva = new Cesta();
            nueva.setUsuario(u);
            nueva.setProductos(new ArrayList<>());
            return nueva;
        });
        if(!cesta.getProductos().contains(p)){
            cesta.getProductos().add(p);
        }
        cestaRepository.save(cesta);
        return true;
    }
    public boolean eliminarProductoDeCesta(AgregarProductoCestaRequest request) {
        String nombreUsuario = request.getNombreUsuario();
        ProductoDto productoDto = request.getProducto();

        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioRepository.findByNombre(nombreUsuario));
        if (usuarioOpt.isEmpty()) {
            return false; // Usuario no existe
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
                        .precio(p.getPrecio().doubleValue())  // si tu precio es BigDecimal, conviértelo a Double
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