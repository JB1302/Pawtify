package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.Pedido;
import com.Pawtify.pawtify.domain.User;
import com.Pawtify.pawtify.repository.PedidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findByUsuario(User usuario) {
        return pedidoRepository.findByUsuario(usuario);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido getPedido(Pedido pedido) {
        return pedidoRepository.findById(pedido.getId()).orElse(null);
    }

    @Transactional
    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Transactional
    public boolean delete(Pedido pedido) {
        try {
            pedidoRepository.delete(pedido);
            pedidoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
