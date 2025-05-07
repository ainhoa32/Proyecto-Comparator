package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.services.ComparadorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

//    @PostMapping("/prueba")
//    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){
//        Ahorramas ahorramas = new Ahorramas();
//        CompletableFuture<List<ProductoDto>> productos = ahorramas.obtenerListaSupermercado("leche");
//        CompletableFuture<Void> completa = CompletableFuture.allOf(productos);
//        completa.join();
//        List<ProductoDto> productosComparados = productos.join();
//        model.addAttribute("productosComparados", productosComparados);
//        return "prueba";
//    }
}
