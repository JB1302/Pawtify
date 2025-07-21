/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Pedido;
import com.Pawtify.pawtify.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jstev
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
      List<Pedido> findByUsuario(User usuario);
        
}
