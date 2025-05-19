package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.CestaRepository;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Objects;

@Service
public class CestaService {

   /* @Autowired
    private CestaRepository cestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Cesta guardarCesta(Cesta cesta) {
        return cestaRepository.save(cesta);
    }

    public Optional<Cesta> obtenerCestaPorId(Integer id) {
        return cestaRepository.findById(id);
    }

    public boolean existeCestaPorId(Integer id) {
        return cestaRepository.existsById(id);
    }

    public boolean eliminarCestaPorId(Integer id) {
        if (cestaRepository.existsById(id)) {
            cestaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean eliminarProductoDeCesta(Integer idUsuario, Integer idProducto) {
        Optional<Cesta> cestaOpt = StreamSupport.stream(cestaRepository.findAll().spliterator(), false)
                .filter(c -> c.getUsuario() != null && Objects.equals(c.getUsuario().getId(), idUsuario))
                .findFirst();

        if (cestaOpt.isPresent()) {
            Cesta cesta = cestaOpt.get();
            String[] productos = cesta.getIdProds().split(",");
            String nuevosProds = Arrays.stream(productos)
                    .map(String::trim)
                    .filter(p -> !p.equals(idProducto.toString()))
                    .collect(Collectors.joining(","));

            cesta.setIdProds(nuevosProds);
            cestaRepository.save(cesta);
            return true;
        }

        return false;
    }

    public Optional<Cesta> obtenerCestaPorUsuario(Usuario usuario) {
        return cestaRepository.findByUsuario(usuario);
    }

    public Cesta agregarProductoAUsuario(String nombreUsuario, Integer idProducto) {
        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Optional<Cesta> cestaOpt = obtenerCestaPorUsuario(usuario);
        Cesta cesta;
        if (cestaOpt.isPresent()) {
            cesta = cestaOpt.get();
            String productos = cesta.getIdProds();
            if (productos == null || productos.isBlank()) {
                cesta.setIdProds(idProducto.toString());
            } else {
                String[] productosArray = productos.split(",");
                boolean existe = Arrays.stream(productosArray)
                        .map(String::trim)
                        .anyMatch(p -> p.equals(idProducto.toString()));
                if (!existe) {
                    cesta.setIdProds(productos + "," + idProducto);
                }
            }
        } else {
            cesta = new Cesta();
            cesta.setUsuario(usuario);
            cesta.setIdProds(idProducto.toString());
        }
        return guardarCesta(cesta);
    }*/


}
