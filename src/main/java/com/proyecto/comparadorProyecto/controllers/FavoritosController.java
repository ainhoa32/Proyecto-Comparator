package com.proyecto.comparadorProyecto.controllers;

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
    @PostMapping("/{nombreUsuario}")
    public ResponseEntity<Favoritos> guardarFavorito(@RequestBody ProductoDto productoDto, @PathVariable String nombreUsuario) {
        FavoritoDTO favoritoDTO = new FavoritoDTO();
        favoritoDTO.setNombre(productoDto.getNombre());
        favoritoDTO.setUsuarioId(favoritosService.obtenerIdUsuario(nombreUsuario));

        Favoritos favoritoGuardado = favoritosService.guardarFavorito(favoritoDTO);
        return ResponseEntity.ok(favoritoGuardado);
    }
}
