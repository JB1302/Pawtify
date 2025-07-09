/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jstev
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Muestra la página de login cuando acceden a /login
    @GetMapping("/login")
    public String login() {
        return "login/listado";
    }

    // Muestra el formulario de registro cuando acceden a /register
    @GetMapping("/register")
    public String registerForm() {
        return "registrar/listado";
    }

    // Procesa el registro de usuario cuando envían el formulario (POST a /register)
    @PostMapping("/register")
    public String registerUser(@RequestParam String nombre,
            @RequestParam String password,
            @RequestParam String email,
            Model model) {
        // Si el usuario ya existe, muestra error en el login
        if (userService.userExists(email)) {
            model.addAttribute("error", "El usuario ya existe");
            return "login/listado";
        }
        // Si no existe, lo registra y redirige al login con mensaje de éxito
        userService.registrarNewUser(nombre, email, password);
        return "redirect:/login?registered";
    }

}
