package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.models.Cesta;
import com.proyecto.comparadorProyecto.services.CestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cesta")
public class CestaController {

    /*@Autowired
    private CestaService cestaService;

    @PostMapping
    public Cesta crearCesta(@RequestBody Cesta cesta) {
        return cestaService.guardarCesta(cesta);
    }

    @GetMapping("/{id}")
    public Optional<Cesta> obtenerCesta(@PathVariable Integer id) {
        return cestaService.obtenerCestaPorId(id);
    }

    @GetMapping
    public Iterable<Cesta> obtenerTodasLasCestas() {
        return cestaService.obtenerTodasLasCestas();
    }

    @GetMapping("/comprobar/{id}")
    public ResponseEntity<String> comprobarCesta(@PathVariable Integer id) {
        boolean existe = cestaService.existeCestaPorId(id);
        if (existe) {
            return ResponseEntity.ok("La cesta con ID " + id + " existe.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCesta(@PathVariable Integer id) {
        boolean eliminado = cestaService.eliminarCestaPorId(id);
        if (eliminado) {
            return ResponseEntity.ok("Cesta eliminada correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-producto")
    public ResponseEntity<String> eliminarProductoDeCesta(
            @RequestParam Integer idUsuario,
            @RequestParam Integer idProducto) {

        boolean eliminado = cestaService.eliminarProductoDeCesta(idUsuario, idProducto);
        if (eliminado) {
            return ResponseEntity.ok("Producto eliminado de la cesta.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
