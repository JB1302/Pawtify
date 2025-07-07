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
    private ProductoRepository productoRepo;

    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {

        return productoRepo.findById(producto.getId()).orElse(null);
    }
    
   
    /*@Transactional(readOnly = true)
    public List<Producto> Buscar(String palabra){
        if(palabra != null){
            return productoRepo.Buscar(palabra);
        }
        */
    
    
    
    @Transactional(readOnly = true)
    public List<Producto> getProductos(){
        var lista = productoRepo.findAll();
        return lista;
    }
    
    @Transactional
    public void save(Producto producto) {
        productoRepo.save(producto);
    }

    @Transactional
    public boolean delete(Producto producto) {
        try {
            productoRepo.delete(producto);
            productoRepo.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

