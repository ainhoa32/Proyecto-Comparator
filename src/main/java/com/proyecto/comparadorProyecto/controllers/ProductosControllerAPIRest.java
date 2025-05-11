package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.services.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productos")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductosControllerAPIRest {
    private final ProductosService productosService;

    @GetMapping("precioGranel/{producto}")
    public ResponseEntity<?> productosComparadosPorPrecioGranel(
            @PathVariable(name = "producto") String producto) {
        return productosService.getComparacionCompleta(producto);
    }

    @GetMapping("precioGranel/{producto}/{supermercados}")
    public ResponseEntity<?> productosComparadosPorPrecio(
            @PathVariable(name = "producto") String producto, @PathVariable(name = "supermercados") String supermercados) {
        return productosService.getComparacionConcreta(producto, supermercados);
    }
}
