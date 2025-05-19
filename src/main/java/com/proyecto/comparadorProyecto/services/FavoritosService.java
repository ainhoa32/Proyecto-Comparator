package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.FavoritosRepository;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritosService {

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Favoritos> obtenerFavoritosPorUsuario(String nombreUsuario) {
        Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return  favoritosRepository.findByUsuario(user);
    }

    public Integer obtenerIdUsuario(String nombreUsuario) {
        Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return user.getId();
    }

    public Favoritos guardarFavorito(FavoritoDTO favoritoDTO) {
        Usuario usuario = usuarioRepository.findByNombre(favoritoDTO.getUsuario());
        Favoritos favorito = new Favoritos();
        favorito.setUsuario(usuario);
        favorito.setNombre(favoritoDTO.getUsuario());
        return favoritosRepository.save(favorito);
    }

    public void borrarFavorito(FavoritoDTO favoritoDTO) {
        Usuario usuario = usuarioRepository.findByNombre(favoritoDTO.getUsuario());
        favoritosRepository.deleteByUsuarioAndNombre(usuario, favoritoDTO.getNombreBusqueda());
    }
}


