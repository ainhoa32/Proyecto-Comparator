package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.AgregarProductoAListaDTO;
import com.proyecto.comparadorProyecto.dto.ListaPredeterminadaDTO;
import com.proyecto.comparadorProyecto.models.Listaspredeterminada;
import com.proyecto.comparadorProyecto.services.ListaPredeterminadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
@CrossOrigin(origins = "*")
public class ListaPredeterminadaController {

    @Autowired
    private ListaPredeterminadaService listaService;

    @GetMapping("/{nombre}")
    public ResponseEntity<?> obtenerListaPorNombre(@PathVariable String nombre) {
        return listaService.obtenerListaPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/agregar")
    public ResponseEntity<String> agregarProducto(@RequestBody AgregarProductoAListaDTO dto) {
        try {
            listaService.agregarProductoALista(dto);
            return ResponseEntity.ok("Producto agregado correctamente a la lista: " + dto.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<String> crearLista(@RequestBody ListaPredeterminadaDTO dto) {
        try {
            listaService.crearLista(dto.getNombre());
            return ResponseEntity.ok("Lista creada correctamente: " + dto.getNombre());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Listaspredeterminada>> obtenerTodas() {
        return ResponseEntity.ok(listaService.obtenerTodasLasListas());
    }

    @GetMapping("/visibles")
    public ResponseEntity<List<Listaspredeterminada>> obtenerVisibles() {
        return ResponseEntity.ok(listaService.obtenerListasVisibles());
    }

    @PutMapping("/visibilidad/{nombre}")
    public ResponseEntity<String> alternarVisibilidad(@PathVariable String nombre) {
        try {
            listaService.alternarVisibilidadPorNombre(nombre);
            return ResponseEntity.ok("Visibilidad de la lista \"" + nombre + "\" ha sido alternada.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
