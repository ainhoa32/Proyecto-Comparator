package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.buscador.models.carrefour.Producto;
import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.dto.ProductoDto;
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
    public ResponseEntity<Favoritos> guardarFavorito(@RequestBody FavoritoDTO favoritos) {
        Favoritos guardado = favoritosService.guardarFavorito(favoritos);
        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping
    public ResponseEntity<?> borrarFavorito(@RequestBody FavoritoDTO favoritoDTO) {
        favoritosService.borrarFavorito(favoritoDTO);
        return ResponseEntity.ok().build();
    }

}
