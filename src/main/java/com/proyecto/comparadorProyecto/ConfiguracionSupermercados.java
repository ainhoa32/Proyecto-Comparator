package com.proyecto.comparadorProyecto;

import com.proyecto.comparadorProyecto.buscador.Supermercado;
import com.proyecto.comparadorProyecto.buscador.supermercados.Ahorramas;
import com.proyecto.comparadorProyecto.buscador.supermercados.Carrefour;
import com.proyecto.comparadorProyecto.buscador.supermercados.Dia;
import com.proyecto.comparadorProyecto.buscador.supermercados.Mercadona;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfiguracionSupermercados {
    @Bean
    List<Supermercado> obtenerSupermercados(Mercadona mercadona, Carrefour carrefour, Ahorramas ahorramas, Dia dia) {
        return List.of(
                mercadona,
                carrefour,
                ahorramas,
                dia
        );
    }
}
