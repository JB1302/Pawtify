package com.Pawtify.pawtify.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/inicio")
public class InicioController {
    
 @GetMapping("/listado")
    
        public String listado(){ 
           

        return "/inicio/listado";

    }
     
 
}
