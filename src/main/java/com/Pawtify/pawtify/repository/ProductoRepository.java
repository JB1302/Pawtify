package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
   //Consulta SQL
    @Query(nativeQuery = true,
            value="SELECT * FROM productos a WHERE a.nombre LIKE CONCAT('%', :texto, '%') OR a.descripcion LIKE CONCAT('%', :texto '%') OR a.tipo_mascota LIKE CONCAT('%', :texto, '%') order by id")
   
    public List<Producto> ConsultaSQL(String texto);
    
}

