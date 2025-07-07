package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.domain.CarritoItem;
import com.Pawtify.pawtify.service.ProductoService;
import com.Pawtify.pawtify.service.CarritoService;
import java.util.List;
import java.util.Locale;
import java.security.Principal;
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);
        model.addAttribute("totalProductos", lista.size());

        return "/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Producto producto, RedirectAttributes redirectAttributes) {
        producto = productoService.getProducto(producto);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
        } else if (false) {
            redirectAttributes.addFlashAttribute("error", "Error: producto tiene asociaciones.");
        } else if (productoService.delete(producto)) {
            redirectAttributes.addFlashAttribute("todoOk", "Producto eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("todoOk", "Error al eliminar el producto.");
        }

        return "redirect:/producto/listado";
    }

    @PostMapping("/modificar")
    public String modificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Producto producto) {
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, Principal principal) {
        try {
            List<CarritoItem> items = carritoService.obtenerItems(principal.getName());
            double total = items.stream()
                    .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                    .sum();
            model.addAttribute("carritoItems", items);
            model.addAttribute("total", total);
            return "/Producto/carrito";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el carrito");
            return "/Producto/carrito";
        }
    }

    @PostMapping("/carrito/agregar/{id}")
    public String agregarAlCarrito(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int cantidad,
            Principal principal,
            RedirectAttributes redirect
    ) {
        carritoService.agregarProducto(id, cantidad, principal.getName());
        redirect.addFlashAttribute("success", "Producto agregado al carrito");
        return "redirect:/producto/listado";
    }
}
