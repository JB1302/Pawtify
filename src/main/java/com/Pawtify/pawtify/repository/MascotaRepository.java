package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}