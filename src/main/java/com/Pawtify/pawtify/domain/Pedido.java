package com.Pawtify.pawtify.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Esto mapea al campo en tu tabla
    private User usuario;

    private LocalDateTime fecha;

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDIENTE','ENVIADO','ENTREGADO','CANCELADO')")
    private EstadoPedido estado;

    public enum EstadoPedido {
        PENDIENTE,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }
}
