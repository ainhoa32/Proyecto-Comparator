package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.AgregarProductoCestaRequest;
import com.proyecto.comparadorProyecto.dto.CestaDTO;
import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.services.CestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cesta")
public class CestaController {

    @Autowired
    private CestaService cestaService;

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<CestaDTO> obtenerCesta(@PathVariable String nombreUsuario) {
        CestaDTO cesta = cestaService.obtenerCestaPorUsuario(nombreUsuario);
        if (cesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cesta);
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarProducto(@RequestBody AgregarProductoCestaRequest request) {
        boolean resultado = cestaService.agregarProductoACesta(request);
        if (resultado) {
            return ResponseEntity.ok("Producto agregado a la cesta");
        } else {
            return ResponseEntity.badRequest().body("Error al agregar producto o usuario no existe");
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarProducto(@RequestBody AgregarProductoCestaRequest request) {
        boolean resultado = cestaService.eliminarProductoDeCesta(request);
        if (resultado) {
            return ResponseEntity.ok("Producto eliminado de la cesta");
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado o usuario inválido");
        }
    }
}
