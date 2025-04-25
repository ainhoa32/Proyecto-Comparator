package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.services.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productos")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductosControllerAPIRest {
    private final ProductosService productosService;

    @GetMapping("precioGranel/{producto}")
    public ResponseEntity<?> productosComparadosPorPrecioGranel(
            @PathVariable(name = "producto") String producto) {
        return productosService.getComparacionCompleta(producto, "precioGranel");
    }

    @GetMapping("precio/{producto}")
    public ResponseEntity<?> productosComparadosPorPrecio(
            @PathVariable(name = "producto") String producto) {
        return productosService.getComparacionCompleta(producto, "precio");
    }
}
