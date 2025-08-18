package com.Pawtify.pawtify.controller;


import com.Pawtify.pawtify.domain.Item;
import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.service.ItemService;
import com.Pawtify.pawtify.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarritoController {
    
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductoService productoService;
   
    @GetMapping("/carrito/listado")
    
        public String listado(Model model){ 
            var lista = itemService.getItems();
            model.addAttribute("listaItems", lista);
            var carritoTotal=itemService.getTotal();
            model.addAttribute("carritoTotal", carritoTotal);
          
        return "/carrito/listado";

    }

       
    @PostMapping("/carrito/guardar")
    public String guardar(Item item) {
        itemService.update(item);
        return "redirect:/carrito/listado";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminar(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }
    
    @GetMapping("/carrito/modificar/{id}")
    public String modificar(Item item, Model model) {
        item = itemService.getItem(item);
        model.addAttribute("item", item);
        return "/carrito/modifica";
    }

    @GetMapping("/carrito/agregar/{id}")
    public ModelAndView agregar(Item item, Model model) {
        Item item2 = itemService.getItem(item);
        if (item2 == null) {
            Producto producto = productoService.getProducto(item);
            item2 = new Item(producto);
        }
        itemService.save(item2);
        var lista = itemService.getItems();
        model.addAttribute("listaItems",lista);
        
        model.addAttribute("carritoTotal",itemService.getTotal());
        
        model.addAttribute("listaTotal",lista.size());
        
        
        //Cambiar esto
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
    
    /*@GetMapping("/facturar/carrito")
    public String facturarCarrito(){
        itemService.facturar();
        return "redirect:/";
    }
    */
}
