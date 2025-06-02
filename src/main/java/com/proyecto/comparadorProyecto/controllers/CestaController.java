package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.ProductoCestaRequest;
import com.proyecto.comparadorProyecto.dto.CestaDTO;
import com.proyecto.comparadorProyecto.dto.UsuarioRequest;
import com.proyecto.comparadorProyecto.services.CestaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cesta")
public class CestaController {

    @Autowired
    private CestaService cestaService;

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<CestaDTO> obtenerCesta(@PathVariable String nombreUsuario) {
        CestaDTO cesta = cestaService.obtenerCestaPorUsuario(nombreUsuario);
        if (cesta == null || cesta.getProductos() == null) {
            CestaDTO vacia = cestaService.crearCestaUserSiNoTiene(nombreUsuario);
            return ResponseEntity.ok(vacia);
        }
        return ResponseEntity.ok(cesta);
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@Valid @RequestBody ProductoCestaRequest request) {
        try {
            cestaService.agregarProductoACesta(request);
            return ResponseEntity.ok("Producto agregado a la cesta");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor."));
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarProducto(@RequestBody ProductoCestaRequest request) {
        try {
            cestaService.eliminarProductoDeCesta(request);
            return ResponseEntity.ok("Producto eliminado de la cesta");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor."));
        }
    }

    @DeleteMapping("/eliminarCesta")
    public ResponseEntity<?> eliminarCesta(@RequestBody UsuarioRequest request) {
        try {
            cestaService.eliminarCesta(request.getNombreUsuario());
            return ResponseEntity.ok("Cesta eliminada correctamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor."));
        }
    }


}