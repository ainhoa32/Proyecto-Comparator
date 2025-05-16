package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.repository.CestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Objects;

@Service
public class CestaService {

    @Autowired
    private CestaRepository cestaRepository;

    public Cesta guardarCesta(Cesta cesta) {
        return cestaRepository.save(cesta);
    }

    public Optional<Cesta> obtenerCestaPorId(Integer id) {
        return cestaRepository.findById(id);
    }

    public Iterable<Cesta> obtenerTodasLasCestas() {
        return cestaRepository.findAll();
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
}
