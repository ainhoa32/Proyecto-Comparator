package com.proyecto.comparadorProyecto.services;

import com.proyecto.comparadorProyecto.dto.FavoritoDTO;
import com.proyecto.comparadorProyecto.models.Favoritos;
import com.proyecto.comparadorProyecto.models.Usuario;
import com.proyecto.comparadorProyecto.repository.FavoritosRepository;
import com.proyecto.comparadorProyecto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritosService {

    private final FavoritosRepository favoritosRepository;
    private final UsuarioRepository usuarioRepository;

    public FavoritosService(FavoritosRepository favoritosRepository, UsuarioRepository usuarioRepository) {
        this.favoritosRepository = favoritosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioByNombre(String nombreUsuario) {
        Optional<Usuario> optionalUsuario = Optional.ofNullable(usuarioRepository.findByNombre(nombreUsuario));
        if (optionalUsuario.isPresent()) {
            return optionalUsuario.get();
        } else {
            throw new RuntimeException("Usuario no encontrado: " + nombreUsuario);
        }
    }


    public List<Favoritos> getAllByUsuario(String nombreUsuario) {
        Usuario usuario = getUsuarioByNombre(nombreUsuario);
        System.out.println(usuario.getId());
        return favoritosRepository.findByUsuarioId(usuario.getId());
    }

    public Favoritos save(Favoritos favorito, String nombreUsuario) {
        Usuario usuario = getUsuarioByNombre(nombreUsuario);
        favorito.setUsuario(usuario);
        return favoritosRepository.save(favorito);
    }

    public void delete(Integer id) {
        favoritosRepository.deleteById(id);
    }

    public List<String> getNombresProductosFavoritos(String nombreUsuario) {
        Usuario usuario = getUsuarioByNombre(nombreUsuario);
        return favoritosRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(f -> f.getProducto().getNombre()) // asumiendo que Producto tiene getNombre()
                .collect(Collectors.toList());
    }

    public List<FavoritoDTO> getFavoritosDTOByUsuario(String nombreUsuario) {
        Usuario usuario = getUsuarioByNombre(nombreUsuario);
        return favoritosRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(f -> new FavoritoDTO(f.getProducto().getNombre(), f.getProducto().getPrecio()))
                .collect(Collectors.toList());
    }

}
