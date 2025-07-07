package com.Pawtify.pawtify.service;

/**
 *
 * @author camil
 */
import com.Pawtify.pawtify.domain.Mascota;
import com.Pawtify.pawtify.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MascotaService {
      @Autowired
    private MascotaRepository mascotaRepo;

    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepo.save(mascota);
    }
}

