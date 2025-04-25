package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.services.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("productos")
@AllArgsConstructor
public class ProductosControllerAPIRest {
        private final ProductosService productosService;

        @GetMapping("{producto}")
    public ResponseEntity<?> productosComparadosSupermercados(
            @PathVariable(name = "producto") String producto) {
            return productosService.getComparacionCompleta(producto);
        }
}
