package com.proyecto.comparadorProyecto.controllers;

import com.proyecto.comparadorProyecto.buscador.supermercados.Carrefour;
import com.proyecto.comparadorProyecto.buscador.supermercados.Dia;
import com.proyecto.comparadorProyecto.buscador.supermercados.Mercadona;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ComparadorProyectoController {

    @GetMapping("/prueba")
    public String prueba(){
        return "prueba";
    }

    @PostMapping("/prueba")
    public String pruebaForm(@ModelAttribute("producto") String producto, Model model){
        Mercadona mercadona = new Mercadona();
        Carrefour carrefour = new Carrefour();
        Dia dia = new Dia();
        mercadona.hacerPeticionSupermercado(producto);
        carrefour.hacerPeticionSupermercado(producto);
        dia.hacerPeticionSupermercado(producto);
        return "prueba";
    }
}
