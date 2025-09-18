package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.domain.CarritoItem;
import com.Pawtify.pawtify.service.ProductoService;
import com.Pawtify.pawtify.service.CarritoService;
import java.util.List;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
        final int UMBRAL_STOCK = 10;

        System.out.println("=== PRODUCTOS CON STOCK BAJO ===");
        lista.stream()
             .filter(p -> p.getStock() < UMBRAL_STOCK)
             .forEach(p -> System.out.println(p.getNombre() + " - Stock: " + p.getStock()));

        model.addAttribute("productos", lista);
        model.addAttribute("totalProductos", lista.size());
        model.addAttribute("umbralStock", UMBRAL_STOCK);

        return "/Producto/listado";
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
        return "/Producto/modifica";
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
        Producto producto = productoService.getProductoById(id);

        if (producto.getStock() < cantidad) {
            redirect.addFlashAttribute("error",
                "No hay suficiente stock de " + producto.getNombre() +
                ". Solo quedan " + producto.getStock() + " unidades.");
        } else {
            carritoService.agregarProducto(id, cantidad, principal.getName());
            redirect.addFlashAttribute("success", "Producto agregado al carrito");
        }

        return "redirect:/producto/listado";
    }

    @PostMapping("/busqueda")
    public String query3(@RequestParam() String texto, Model model) {
        var productos = productoService.consultaSQL(texto);
        model.addAttribute("productos", productos);
        model.addAttribute("texto", texto);

        return "/Producto/listado";
    }
}
