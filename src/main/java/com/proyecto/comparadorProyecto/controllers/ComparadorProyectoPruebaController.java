package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.ProductoDto;
import com.proyecto.comparadorProyecto.services.ComparadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ComparadorProyectoPruebaController {
    private final ComparadorService comparadorService;

    public ComparadorProyectoPruebaController(final ComparadorService comparadorService) {
        this.comparadorService = comparadorService;
    }

    @GetMapping("/prueba")
    public String prueba(){
        return "prueba";
    }

    @PostMapping("/prueba")
    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){

        List<ProductoDto> productosComparados = comparadorService.obtenerListaProductosComparados(producto);
        model.addAttribute("productosComparados", productosComparados);
        return "prueba";
    }
}
