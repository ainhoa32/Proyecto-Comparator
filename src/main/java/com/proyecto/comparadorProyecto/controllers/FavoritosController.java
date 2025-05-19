package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
class FavoritosController {
    private final FavoritosService favoritosService;

    @Autowired
    public FavoritosController(FavoritosService favoritosService) {
        this.favoritosService = favoritosService;
    }

    @GetMapping("/{nombreUsuario}")
    public List<Favoritos> getFavoritos(@PathVariable String nombreUsuario) {
        return favoritosService.getAllByUsuario(nombreUsuario);
    }

    @GetMapping("/{nombreUsuario}/resumen")
    public List<FavoritoDTO> getResumenFavoritos(@PathVariable String nombreUsuario) {
        return favoritosService.getFavoritosDTOByUsuario(nombreUsuario);
    }

    @PostMapping("/{nombreUsuario}")
    public Favoritos guardarFavorito(@RequestBody Favoritos favorito, @PathVariable String nombreUsuario) {
        return favoritosService.save(favorito, nombreUsuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarFavorito(@PathVariable Integer id) {
        favoritosService.delete(id);
    }

    @GetMapping("/{nombreUsuario}/nombres")
    public List<String> getNombresFavoritos(@PathVariable String nombreUsuario) {
        return favoritosService.getNombresProductosFavoritos(nombreUsuario);
    }
}
