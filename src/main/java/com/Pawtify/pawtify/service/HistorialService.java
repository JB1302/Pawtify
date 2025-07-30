/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.Historial;
import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.repository.HistorialRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jstev
 */
@Service
public class HistorialService {
    
    @Autowired
    private HistorialRepository historialRepository;
    
    //Metodo para leer registros de la tabla Historial
    @Transactional(readOnly = true)
    public List<Historial> getHistorials(boolean activo) {
        var lista = historialRepository.findAll();

        return lista;
    }

    //
    @Transactional(readOnly = true)
    public Historial getHistorial(Historial historial) {

        return historialRepository.findById(historial.getId())
                .orElse(null);
    }

    
    
    @Transactional
    public void save(Historial historial) {
        historialRepository.save(historial);
    }
    
    
    //Borrar Registros
    @Transactional
    public boolean delete(Historial historial) {
        try {
            historialRepository.delete(historial);
            //Cuando se manda a borrar se manda un hilo
            //Ese hilo se va a la DB y borra.
            //Mientras eso se hace, se corre en el background
            
            //No continuar hasta que el proceso finalice
            historialRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Historial> consultaSQL(String texto) {

        return historialRepository.ConsultaSQL(texto);
    }

}
