package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.FavoritosRepository;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean existeFavorito(String nombreUsuario, String nombreFavorito) {
        Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return !favoritosRepository.findByUsuarioAndNombre(user, nombreFavorito).isEmpty();
    }

    public Integer obtenerIdUsuario(String nombreUsuario) {
        Usuario user = usuarioRepository.findByNombre(nombreUsuario);
        return user.getId();
    }

    public Favoritos guardarFavorito(FavoritoDTO favoritoDTO) {
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByNombre(favoritoDTO.getUsuario()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        List<Favoritos> favoritosActuales = favoritosRepository.findByUsuario(usuario);
        if (favoritosActuales.size() >= 8) {
            throw new IllegalStateException("El usuario ya tiene el máximo de 8 favoritos.");
        }

        if (!favoritosRepository.findByUsuarioAndNombre(usuario, favoritoDTO.getNombreBusqueda()).isEmpty()) {
            throw new IllegalStateException("Este favorito ya existe para el usuario.");
        }

        Favoritos favorito = new Favoritos();
        favorito.setUsuario(usuario);
        favorito.setNombre(favoritoDTO.getNombreBusqueda());

        return favoritosRepository.save(favorito);
    }
    @Transactional
    public void borrarFavorito(FavoritoDTO favoritoDTO) {
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByNombre(favoritoDTO.getUsuario()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        int eliminados = favoritosRepository.deleteByUsuarioAndNombre(usuario, favoritoDTO.getNombreBusqueda());

        if (eliminados == 0) {
            throw new IllegalStateException("El favorito no existe o ya fue eliminado.");
        }
    }

    public List<FavoritoDTO> convertirAFavoritoDTO(List<Favoritos> favoritos) {
        return favoritos.stream()
                .map(f -> new FavoritoDTO(f.getUsuario().getNombre(), f.getNombre()))
                .collect(Collectors.toList());
    }
}


