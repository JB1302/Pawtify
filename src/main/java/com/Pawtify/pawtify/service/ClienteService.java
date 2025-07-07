package com.Pawtify.pawtify.service;


import com.Pawtify.pawtify.domain.Cliente;
import com.Pawtify.pawtify.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepo;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepo.findById(id);
    }
}

