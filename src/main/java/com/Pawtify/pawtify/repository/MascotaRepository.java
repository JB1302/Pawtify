package com.Pawtify.pawtify.repository;

import com.Pawtify.pawtify.domain.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    @Query(nativeQuery = true,
            value="SELECT * FROM mascota a WHERE a.id LIKE CONCAT('%', :texto, '%')"
                    + "OR a.nombre LIKE CONCAT('%', :texto '%') "
                    + "OR a.raza LIKE CONCAT('%', :texto, '%') "
                    + "OR a.color LIKE CONCAT('%', :texto '%') "
                    + "OR a.edad LIKE CONCAT('%', :texto '%') "
                    + "OR a.peso LIKE CONCAT('%', :texto '%') "
                    + "OR a.especie LIKE CONCAT('%', :texto '%') "
                    + "OR a.cliente_id LIKE CONCAT('%', :texto '%') "
                    + "order by id"
     )
    
     public List<Mascota> ConsultaSQL(String texto);
}
