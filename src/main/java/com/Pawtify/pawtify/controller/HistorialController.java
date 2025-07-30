/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.Historial;
import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.repository.UserRepository;
import com.Pawtify.pawtify.service.CarritoService;
import com.Pawtify.pawtify.service.HistorialService;
import com.Pawtify.pawtify.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;


    
        @Autowired
    private UserRepository userRepository;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/eliminar")
    public String eliminar(Historial historial, RedirectAttributes redirectAttributes) {
        historial = historialService.getHistorial(historial);

        if (historial == null) {
            redirectAttributes.addFlashAttribute("error", "Historial no encontrado.");
        } else if (false) {
            redirectAttributes.addFlashAttribute("error", "Error: historial tiene asociaciones.");
        } else if (historialService.delete(historial)) {
            redirectAttributes.addFlashAttribute("todoOk", "Historial eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("todoOk", "Error al eliminar el historial.");
        }

        return "redirect:/historial/listado";
    }

    @PostMapping("/modificar")
    public String modificar(Historial historial, Model model) {
        historial = historialService.getHistorial(historial);
        model.addAttribute("historial", historial);
        return "/historial/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Historial historial) {
        historialService.save(historial);
        return "redirect:/historial/listado";
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        // Pobla la lista de historial (stock)
        var lista = historialService.getHistorials(false);
        model.addAttribute("historial", lista);
        model.addAttribute("totalHistorial", lista.size());

        // Autenticación del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Lógica de pedidos según rol
        if (user.getRol().equalsIgnoreCase("ADMIN")) {
            model.addAttribute("pedidos", pedidoService.findAll());
        } else {
            model.addAttribute("pedidos", pedidoService.findByUsuario(user));
        }

        return "historial/listado";
    }
    
    @PostMapping("/busqueda")
    public String query3( @RequestParam() String texto, Model model) {
        var lista=historialService.consultaSQL(texto);
        model.addAttribute ("historial", lista);
        model.addAttribute ("texto", texto);

       

        return "/historial/listado";
    }

}
