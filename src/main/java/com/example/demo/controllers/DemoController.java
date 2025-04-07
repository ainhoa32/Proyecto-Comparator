package com.example.demo.controllers;

import com.example.demo.buscador.supermercados.Mercadona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/prueba")
    public String prueba(){
        Mercadona mercadona = new Mercadona();
        mercadona.comparar();
        return "prueba";
    }
}
