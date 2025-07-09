package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.Mascota;
import com.Pawtify.pawtify.service.MascotaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/perfil")
    public String perfilMascotaSinId(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Debe especificar una mascota.");
        return "redirect:/mascota/listado";
    }

    @GetMapping("/perfil/{id}")
    public String verPerfilMascota(@PathVariable Long id, Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {

        Mascota mascota = mascotaService.getMascotaPorId(id);

        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "Mascota no encontrada.");
            return "redirect:/mascota/listado";
        }

        model.addAttribute("mascota", mascota);
        return "Mascota/perfilMascota";
    }

    @GetMapping("/listado")
    public String listadoMascotas(Model model) {
        List<Mascota> mascotas = mascotaService.getTodasLasMascotas();
        model.addAttribute("mascotas", mascotas);
        return "Mascota/listadoMascotas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaMascota(Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("mascota", new Mascota());
        return "Mascota/editarMascota";
    }

    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable Long id, Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {

        Mascota mascota = mascotaService.getMascotaPorId(id);

        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "Mascota no encontrada.");
            return "redirect:/mascota/listado";
        }

        model.addAttribute("mascota", mascota);
        return "Mascota/editarMascota";
    }

    @PostMapping("/guardar")
    public String guardarMascota(Mascota mascota,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {

        mascotaService.guardarMascota(mascota);
        redirectAttributes.addFlashAttribute("mensaje", "Mascota guardada correctamente.");
        return "redirect:/mascota/perfil/" + mascota.getId();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {

        Mascota mascota = mascotaService.getMascotaPorId(id);

        if (mascota != null) {
            mascotaService.eliminarMascota(id);
            redirectAttributes.addFlashAttribute("mensaje", "Mascota eliminada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Mascota no encontrada.");
        }

        return "redirect:/mascota/listado";
    }
}
