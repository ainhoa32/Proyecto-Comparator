package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.ProductoAListaDTO;
import com.proyecto.comparadorProyecto.dto.ListaPredeterminadaDTO;
import com.proyecto.comparadorProyecto.models.ListasPredeterminada;
import com.proyecto.comparadorProyecto.services.ListaPredeterminadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaPredeterminadaController {

    @Autowired
    private ListaPredeterminadaService listaService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{nombre}")
    public ResponseEntity<?> obtenerListaPorNombre(@PathVariable String nombre) {
        return listaService.obtenerListaPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarProducto(@RequestBody ProductoAListaDTO dto) {
        try {
            listaService.agregarProductoALista(dto);
            return ResponseEntity.ok("Producto agregado correctamente a la lista: " + dto.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<String> crearLista(@RequestBody ListaPredeterminadaDTO dto) {
        try {
            listaService.crearLista(dto.getNombre());
            return ResponseEntity.ok("Lista creada correctamente: " + dto.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todas")
    public ResponseEntity<List<ListasPredeterminada>> obtenerTodas() {
        return ResponseEntity.ok(listaService.obtenerTodasLasListas());
    }

    @GetMapping("/visibles")
    public ResponseEntity<List<ListasPredeterminada>> obtenerVisibles() {
        return ResponseEntity.ok(listaService.obtenerListasVisibles());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/visibilidad/{nombre}")
    public ResponseEntity<String> alternarVisibilidad(@PathVariable String nombre) {
        try {
            listaService.alternarVisibilidadPorNombre(nombre);
            return ResponseEntity.ok("Visibilidad de la lista \"" + nombre + "\" ha sido alternada.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{nombre}")
    public ResponseEntity<String> eliminarListaYProductos(@PathVariable String nombre) {
        try {
            listaService.eliminarListaYProductosPorNombre(nombre);
            return ResponseEntity.ok("Lista y productos eliminados correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar-producto")
    public ResponseEntity<String> eliminarProductoDeLista(@RequestBody ProductoAListaDTO dto) {
        try {
            listaService.eliminarProductoDeLista(dto);
            return ResponseEntity.ok("Producto eliminado correctamente de la lista.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
