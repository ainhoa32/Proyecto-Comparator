package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<List<FavoritoDTO>> getFavoritosByNombreUsuario(@PathVariable String nombreUsuario) {
        List<FavoritoDTO> favoritosDTO = favoritosService.convertirAFavoritoDTO(favoritosService.obtenerFavoritosPorUsuario(nombreUsuario));
        return ResponseEntity.ok(favoritosDTO);
    }

    @PostMapping
    public ResponseEntity<?> guardarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        try {
            favoritosService.guardarFavorito(favoritoDTO);
            return ResponseEntity.ok("Guardado correctamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor."));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> borrarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        try {
            favoritosService.borrarFavorito(favoritoDTO);
            return ResponseEntity.ok("Eliminado correctamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor."));
        }
    }
}
