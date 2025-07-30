/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Historial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jstev
 */
public interface HistorialRepository extends JpaRepository<Historial, Long>{
     @Query(nativeQuery = true,
            value="SELECT * FROM stock_historial a WHERE a.id_producto LIKE CONCAT('%', :texto, '%') OR a.cambio LIKE CONCAT('%', :texto '%') OR a.motivo LIKE CONCAT('%', :texto, '%')OR a.fecha LIKE CONCAT('%', :texto '%')order by id"
     )
   
    public List<Historial> ConsultaSQL(String texto);
}
