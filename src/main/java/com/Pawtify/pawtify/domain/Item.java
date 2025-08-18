package com.Pawtify.pawtify.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@SuppressWarnings("serial")
public class Item extends Producto {
    private int cantidad;

   
    public Item() {
    }

    public Item(Producto producto) {
        super.setId(producto.getId());
        super.setDescripcion(producto.getDescripcion());
        super.setPrecio(producto.getPrecio());
        super.setActivo(producto.isActivo());
        super.setStock(producto.getStock());
        super.setTipoMascota(producto.getTipoMascota());
        this.cantidad = 0;
    }
}

