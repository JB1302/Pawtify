package com.Pawtify.pawtify.service;

import com.Pawtify.pawtify.domain.Producto;
import com.Pawtify.pawtify.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {


    @Autowired
    private ProductoRepository productoRepository;

    //Metodo para leer registros de la tabla Producto
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        var lista = productoRepository.findAll();

        return lista;
    }

    //
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {

        return productoRepository.findById(producto.getId())
                .orElse(null);
    }

    
    
    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }
    
    
    //Borrar Registros
    @Transactional
    public boolean delete(Producto producto) {
        try {
            productoRepository.delete(producto);
            //Cuando se manda a borrar se manda un hilo
            //Ese hilo se va a la DB y borra.
            //Mientras eso se hace, se corre en el background
            
            //No continuar hasta que el proceso finalice
            productoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(String texto) {

        return productoRepository.ConsultaSQL(texto);
    }
}