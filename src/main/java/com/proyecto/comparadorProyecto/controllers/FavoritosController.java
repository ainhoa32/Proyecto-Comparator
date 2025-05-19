package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<List<Favoritos>> getFavoritosByNombreUsuario(@PathVariable String nombreUsuario) {
        List<Favoritos> favoritos = favoritosService.obtenerFavoritosPorUsuario(nombreUsuario);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping
    public ResponseEntity<String> guardarFavorito(@RequestBody FavoritoDTO favoritos) {
        System.out.println(favoritos.getUsuario());
        System.out.println(favoritos.getNombreBusqueda());
        if (favoritosService.existeFavorito(favoritos.getUsuario(), favoritos.getNombreBusqueda())) {
            favoritosService.guardarFavorito(favoritos);
            return ResponseEntity.ok("Guardado correctamente");
        } else {
            return ResponseEntity.badRequest().body("El favorito ya existe para este usuario.");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> borrarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        if(!favoritosService.existeFavorito(favoritoDTO.getUsuario(), favoritoDTO.getNombreBusqueda())) {
             favoritosService.borrarFavorito(favoritoDTO);
            return ResponseEntity.ok("Eliminado correctamente");
        }else{
            return ResponseEntity.badRequest().body("El producto que intenta eliminar no existe en favoritos");
        }
    }

}
