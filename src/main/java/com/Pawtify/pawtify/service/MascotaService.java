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

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepo;

    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepo.save(mascota);
    }

    public Mascota getMascotaPorId(Long id) {
        return mascotaRepo.findById(id).orElse(null);
    }

    public Optional<Mascota> buscarPorId(Long id) {
        return mascotaRepo.findById(id);
    }

    public void eliminarMascota(Long id) {
        mascotaRepo.deleteById(id);
    }

    public List<Mascota> getTodasLasMascotas() {
        return mascotaRepo.findAll();
    }
}