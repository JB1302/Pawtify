/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jstev
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Busca un usuario por su email. Devuelve Optional por si no existe el usuario.
    Optional<User> findByEmail(String email);
}
