package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
   
}
