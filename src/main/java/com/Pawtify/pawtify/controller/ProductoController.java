package com.Pawtify.pawtify.controller;


import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.service.ProductoService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
     
    
       /* ESTO ES PARA EL EVENTUAL FILTRO
        public String listado(Model model, @Param("palabra") String palabra) {

        var lista = productoService.Buscar(palabra);

        model.addAttribute("productos", lista);
        model.addAttribute("palabra", palabra);

        return "/producto/listado";

    }
        
        
        
*/
        
    @Autowired
    private MessageSource messageSource;
 
    @PostMapping("/eliminar")
    public String eliminar(Producto producto, RedirectAttributes redirectAttributes) {
        producto = productoService.getProducto(producto);
        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("producto.error01", null, Locale.getDefault()));
        } else if (false) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("producto.error02", null, Locale.getDefault()));
        } else if (productoService.delete(producto)) {
            redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        }else{
             redirectAttributes.addFlashAttribute("error",messageSource.getMessage("producto.error03",null, Locale.getDefault()));
        }
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
    
    @PostMapping("/modificar")
    public String modificar(Producto producto, Model model) {
        producto=productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }
    
    
    
    @PostMapping("/guardar")
    public String guardar(Producto producto) {
        productoService.save(producto);
           
        return "redirect:/producto/listado";
    }
        
        
        
        
}
