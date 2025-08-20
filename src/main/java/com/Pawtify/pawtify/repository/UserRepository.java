package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Busca un usuario por su email. Devuelve Optional por si no existe el usuario.
    Optional<User> findByEmail(String email);

    public User findByNombre(String nombre);

    //Para activar el usuario
    public User findByNombreOrEmail(String nombre, String email);

    //Consulta Ampliada - Se usa para validar si un "username" o "email" han
    //sido utilizados por otro usuario
    public boolean existsByNombreOrEmail(String nombre, String email);

    public User findByNombreAndPassword(String nombre, String password);

}
