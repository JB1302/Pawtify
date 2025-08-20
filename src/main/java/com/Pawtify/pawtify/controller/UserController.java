/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.service.UserService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUsers", users.size());
        return "/user/listado";
    }

    @PostMapping("/guardar")
    public String userGuardar(User user) {
        userService.save(user, true); // true = actualización; pon false si quieres tratarlo como alta
        return "redirect:/user/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(User user, RedirectAttributes redirectAttributes) {
        user = userService.getUser(user);
        if (user == null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    messageSource.getMessage("user.error01", null, Locale.getDefault())
            );
        } else if (userService.delete(user)) {
            redirectAttributes.addFlashAttribute(
                    "todoOk",
                    messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault())
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "error",
                    messageSource.getMessage("user.error03", null, Locale.getDefault())
            );
        }
        return "redirect:/user/listado";
    }

    @PostMapping("/modificar")
    public String modificar(User user, Model model) {
        user = userService.getUser(user);
        model.addAttribute("user", user);
        // Asegúrate de que tu template sea /user/modifica.html
        return "/user/modifica";
    }

}
