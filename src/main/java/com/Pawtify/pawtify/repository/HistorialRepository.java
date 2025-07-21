/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jstev
 */
public interface HistorialRepository extends JpaRepository<Historial, Long>{
    
}
