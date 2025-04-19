package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.servicios.ServicioComparador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ComparadorProyectoController {

    @GetMapping("/prueba")
    public String prueba(){
        return "prueba";
    }

    @PostMapping("/prueba")
    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){

        ServicioComparador servicioComparador = new ServicioComparador();
        List<List> productosComparados = servicioComparador.obtenerListaProductosComparados(producto);
        model.addAttribute("productosComparados", productosComparados);
        return "prueba";
    }
}
