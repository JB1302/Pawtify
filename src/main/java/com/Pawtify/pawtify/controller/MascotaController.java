package com.Pawtify.pawtify.controller;

/**
 *
 * @author camil
 */
import com.Pawtify.pawtify.domain.Mascota;
import com.Pawtify.pawtify.domain.Cliente;
import com.Pawtify.pawtify.service.MascotaService;
import com.Pawtify.pawtify.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@Controller
@RequestMapping("/mascotas")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;
    @Autowired
    private ClienteService clienteService;
    @GetMapping
    public String listarMascotas(Model model) {
        model.addAttribute("listaMascotas", mascotaService.listarTodos());
        return "mascota/listado";
    }
    
    @GetMapping("/nueva")
    public String mostrarFormularioMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascota/registro";
    }
    @PostMapping("/guardar")
    public String guardarMascota(
        @ModelAttribute Mascota mascota,
        @RequestParam("clienteNombre") String clienteNombre,
        @RequestParam(value = "clienteCorreo", required = false) String clienteCorreo,
        @RequestParam(value = "clienteTelefono", required = false) String clienteTelefono) {
        if (clienteNombre != null && !clienteNombre.trim().isEmpty()) {
            Cliente cliente = new Cliente();
            cliente.setNombre(clienteNombre.trim());
            cliente.setCorreo(clienteCorreo != null ? clienteCorreo.trim() : null);
            cliente.setTelefono(clienteTelefono != null ? clienteTelefono.trim() : null);
            clienteService.guardarCliente(cliente);
            mascota.setCliente(cliente);
        } else {
            mascota.setCliente(null);
        }
        mascotaService.save(mascota);
        return "redirect:/mascotas";
    }
    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable Long id, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(id);
        if (mascotaOpt.isPresent()) {
            model.addAttribute("mascota", mascotaOpt.get());
            return "mascota/registro";
        }
        return "redirect:/mascotas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarPorId(id);
        return "redirect:/mascotas";
    }

    @GetMapping("/ver/{id}")
    public String verMascota(@PathVariable Long id, Model model) {
        Optional<Mascota> mascotaOpt = mascotaService.buscarPorId(id);
        if (mascotaOpt.isPresent()) {
            model.addAttribute("mascota", mascotaOpt.get());
            return "mascota/perfil";
        }
        return "redirect:/mascotas";
    }
    
    @PostMapping("/busqueda")
    public String query3( @RequestParam() String texto, Model model) {
        var lista=mascotaService.consultaSQL(texto);
        model.addAttribute ("listaMascotas", lista);
        model.addAttribute ("texto", texto);

        return "mascota/listado";
    }
}

