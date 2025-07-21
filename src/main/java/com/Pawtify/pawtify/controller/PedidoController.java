package com.Pawtify.pawtify.controller;

import com.Pawtify.pawtify.repository.UserRepository;
import com.Pawtify.pawtify.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PedidoService pedidoService;
    
    //Migrado a Historial Controller para poder cargar ambos

}
