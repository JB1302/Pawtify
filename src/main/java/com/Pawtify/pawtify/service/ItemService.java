package com.Pawtify.pawtify.service;


import com.Pawtify.pawtify.domain.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class ItemService {
    
    @Autowired
    private HttpSession session;
    
    //Se define un objeto único para todos los items y se crea automáticamente...
  
    public List<Item> getItems(){
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
 
        return lista;
    }
    
    public Item getItem(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        if (lista != null) {
            for (Item i : lista) {
                if (i.getId() == item.getId()) {
                    return i;
                }
            }
        }

        return null;
    }

   public void save(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
 
        if (lista == null) { // Si NO existe, créala
            lista = new ArrayList<>();
        }
 
        //Producto aun no esta en la lista
        boolean existe = false;
 
        for (Item i : lista) {
            if (Objects.equals(i.getId(), item.getId())) {
                existe = true;
                //Verificar Existencias (-1 porque ya esta en el carrito)
                if (i.getCantidad() < i.getStock()) {
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
            }
        }
        if (!existe) {
            item.setCantidad(1);
            lista.add(item);
        }
        session.setAttribute("listaItems", lista);
    }

    public void delete(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        if (lista != null) {//si la lista existe
            var posicion = -1;
            boolean existe = false;
            for (Item i : lista) {
                posicion++;
                if (Objects.equals(i.getId(), item.getId())) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                lista.remove(posicion);
                session.setAttribute("listaItems", lista);
            }
        }
    }

    public void update(Item item) {
        @SuppressWarnings("unchecked")
       List<Item> lista = (List) session.getAttribute("listaItems");
        if (lista != null) {//si la lista existe
            for (Item i : lista) {
                if (Objects.equals(i.getId(), item.getId())){
                   i.setCantidad(item.getCantidad());
                    session.setAttribute("listaItems", lista);
                   break;
                }
               
            }      
        }    
    }
    
    public double getTotal(){
        @SuppressWarnings("unchecked")
       List<Item> lista = (List) session.getAttribute("listaItems");
        double total=0.0;
        if (lista != null) {//si la lista existe
            for (Item i : lista) {
                total+=i.getCantidad()*i.getPrecio();
            }      
        }    
        return total;
    }
    

    
}
