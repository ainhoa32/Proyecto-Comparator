package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.ProductoDto;
import com.proyecto.comparadorProyecto.servicios.ServicioComparador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ComparadorProyectoController {
    private final ServicioComparador servicioComparador;

    public ComparadorProyectoController(final ServicioComparador servicioComparador) {
        this.servicioComparador = servicioComparador;
    }

    @GetMapping("/prueba")
    public String prueba(){
        return "prueba";
    }

    @PostMapping("/prueba")
    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){

        List<ProductoDto> productosComparados = servicioComparador.obtenerListaProductosComparados(producto);
        model.addAttribute("productosComparados", productosComparados);
        return "prueba";
    }
}
