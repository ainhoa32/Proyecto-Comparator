package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.dto.ProductoDto;
import com.proyecto.comparadorProyecto.services.ComparadorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productos")
@AllArgsConstructor
public class ComparadorProyectoController {
//    private final ComparadorService comparadorService;
//
//    public ComparadorProyectoController(final ComparadorService comparadorService) {
//        this.comparadorService = comparadorService;
//    }
//
//    @GetMapping("/prueba")
//    public String prueba(){
//        return "prueba";
//    }
//
//    @PostMapping("/prueba")
//    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){
//
//        List<ProductoDto> productosComparados = comparadorService.obtenerListaProductosComparados(producto);
//        model.addAttribute("productosComparados", productosComparados);
//        return "prueba";
//    }

}
