package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.Cliente;
import com.Pawtify.pawtify.domain.Mascota;
import com.Pawtify.pawtify.service.ClienteService;
import com.Pawtify.pawtify.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/nuevo")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/registro";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/cliente/nuevo?exito";
    }

    @GetMapping("/{id}/mascota")
    public String mostrarFormularioMascota(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id).orElse(null);
        if (cliente == null) return "redirect:/cliente/nuevo?error";
        Mascota mascota = new Mascota();
        mascota.setCliente(cliente);
        model.addAttribute("mascota", mascota);
        return "mascota/registro";
    }

    @PostMapping("/mascota/guardar")
    public String guardarMascota(@ModelAttribute Mascota mascota) {
        mascotaService.guardarMascota(mascota);
        return "redirect:/cliente/" + mascota.getCliente().getId() + "/mascota?exito";
    }
}
