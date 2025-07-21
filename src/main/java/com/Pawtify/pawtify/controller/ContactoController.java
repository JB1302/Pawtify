package com.Pawtify.pawtify.controller;

/**
 *
 * @author camil
 */
import com.Pawtify.pawtify.domain.MensajeContacto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new MensajeContacto());
        return "contacto";
    }

    @PostMapping("/enviar")
    public String procesarFormulario(@ModelAttribute MensajeContacto mensaje, Model model) {
        System.out.println("Mensaje recibido de: " + mensaje.getNombre());
        System.out.println("Correo: " + mensaje.getCorreo());
        System.out.println("Asunto: " + mensaje.getAsunto());
        System.out.println("Mensaje: " + mensaje.getMensaje());

        model.addAttribute("exito", "¡Gracias por contactarnos!");
        model.addAttribute("mensaje", new MensajeContacto());
        return "contacto";
    }
}
