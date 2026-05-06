package com.yavirac.homies.detallePedido;

import java.math.BigDecimal;

import com.yavirac.homies.pedido.PedidoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_pedido")
public class DetallePedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detalle_pedido;

    @Column(nullable = false)
    private BigDecimal iva;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "cantidad_detalle_pedido", nullable = false)
    private Integer cantidad;

    @Column(name = "talla_detalle_pedido", nullable = false)
    private String talla;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "talla_producto_id", nullable = false)
    private TallaProductoEntity tallaProducto;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoEntity pedido; 
}
