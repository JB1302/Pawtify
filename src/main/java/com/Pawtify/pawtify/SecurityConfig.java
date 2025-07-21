package com.Pawtify.pawtify;

import com.Pawtify.pawtify.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Marca esta clase como una clase de configuración de Spring
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    // Este método define la configuración del filtro de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configuración de las reglas de autorización
        http
                .authorizeHttpRequests(authz -> authz
                    // Permite el acceso sin autenticación a estas rutas
                    .requestMatchers(
                        "/", // Pagina Principal
                        "/register", // Pagina Registrar
                        "/login", // Pagina Login
                        "/inicio/**", // Inicio
                        "/webjars/**", // Bootsrap
                        "/css/**", // CSS
                        "/js/**" // JS
                    ).permitAll()
                    // Cualquier otra ruta requiere autenticación
                    .anyRequest().authenticated()
                )
                // Configura el login
                .formLogin(form -> form
                    .loginPage("/login") // Página personalizada de login
                    .defaultSuccessUrl("/", true) // Redirige al inicio después de login
                    .failureUrl("/login?error") // <-- ¡Esto se debe mantener!
                    .permitAll() // Permite acceso a todos al login
                )
                // Permite logout para todos
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll() // Permitir a cualquier desloguearse
                );

        return http.userDetailsService(userDetailsServiceImpl).build();
    }

    // Codificar Contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt genera contraseñas cifradas seguras
        return new BCryptPasswordEncoder();
    }
}
