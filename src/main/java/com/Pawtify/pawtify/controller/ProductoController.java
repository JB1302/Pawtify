package com.Pawtify.pawtify.controller;


import com.Pawtify.pawtify.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/producto")
public class ProductoController {
    
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    
        public String listado(Model model){ 
            var lista = productoService.getProductos();
          
            model.addAttribute("productos", lista);
            

        return "/producto/listado";

    }
     
 
}
