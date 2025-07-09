package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Este método se llama automáticamente al intentar hacer login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca el usuario por email. Si no existe, lanza excepción.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Devuelve un objeto UserDetails listo para que Spring Security valide el login
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol() != null ? user.getRol().toUpperCase() : "CLIENTE")
                .build();
    }

}
