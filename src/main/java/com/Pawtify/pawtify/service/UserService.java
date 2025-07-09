/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author jstev
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Metodo para registrar cliente
    public User registrarNewUser(String nombre, String email, String password) {
        User user = new User();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); //Guardar la pass codificada
        user.setRol("CLIENTE");
        user.setFecha_registro(LocalDateTime.now()); // la fecha actual
        return userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
