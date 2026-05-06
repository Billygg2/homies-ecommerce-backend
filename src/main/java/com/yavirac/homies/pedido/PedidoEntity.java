package com.yavirac.homies.pedido;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import com.yavirac.homies.User.User;
import com.yavirac.homies.detallePedido.DetallePedidoEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;

    @Column( name = "fecha", nullable = false)
    private Timestamp pedido_fecha;

    @Column( name = "confirmacion", nullable = false)
    private Boolean estado;

    @Column(name = "direccion_pedido", length = 200, nullable = true)
    private String direccion;

    // Para la factura del usuario
    @Column(nullable = true)
    private Timestamp fecha_factura;

    @Column(nullable = true)
    private BigDecimal total_factura;

    @Column(name = "imagen_voucher", nullable = true)
    private String img_voucher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetallePedidoEntity> detallePedido;
}
