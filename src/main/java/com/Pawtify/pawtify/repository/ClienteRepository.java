package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  }
