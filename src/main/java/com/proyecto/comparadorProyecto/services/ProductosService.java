package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.ProductoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductosService {
    private final ComparadorService comparadorService;

    public ResponseEntity<?> getComparacionCompleta(
            @PathVariable(name = "producto") String producto){
        List<ProductoDto> listaProductos = comparadorService.obtenerListaProductosComparadosTotal(producto);
        return ResponseEntity.ok().body(listaProductos);
    }

    public ResponseEntity<?> getComparacionConcreta(
            @PathVariable(name = "producto") String producto, @PathVariable(name = "supermercados") String supermercados){
        List<ProductoDto> listaProductos = comparadorService.obtenerListaProductosComparadosConcreto(producto, supermercados);
        return ResponseEntity.ok().body(listaProductos);
    }
}
