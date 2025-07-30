package com.Pawtify.pawtify.service;

/**
 *
 * @author camil
 */
import com.Pawtify.pawtify.domain.Mascota;
import com.Pawtify.pawtify.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listarTodos() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> buscarPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        mascotaRepository.deleteById(id);
    }

    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }
    
    @Transactional(readOnly = true)
    public List<Mascota> consultaSQL(String texto) {

        return mascotaRepository.ConsultaSQL(texto);
    }
}
