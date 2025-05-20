package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<String> guardarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        if (favoritosService.existeFavorito(favoritoDTO.getUsuario(), favoritoDTO.getNombreBusqueda())) {
            return ResponseEntity.badRequest().body("El favorito ya existe para este usuario.");
        } else {
            favoritosService.guardarFavorito(favoritoDTO);
            return ResponseEntity.ok("Guardado correctamente");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> borrarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        if (favoritosService.existeFavorito(favoritoDTO.getUsuario(), favoritoDTO.getNombreBusqueda())) {
            favoritosService.borrarFavorito(favoritoDTO);
            return ResponseEntity.ok("Eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("El producto que intenta eliminar no existe en favoritos");
        }
    }
}
