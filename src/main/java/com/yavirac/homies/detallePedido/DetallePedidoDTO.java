package com.yavirac.homies.detallePedido;

import java.math.BigDecimal;

import com.yavirac.homies.pedido.PedidoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Long id_detalle_pedido;
    private BigDecimal iva;
    private BigDecimal subtotal;
    private BigDecimal total;
    private Integer cantidad;
    private String talla;
    private TallaProductoEntity tallaProducto;
    private PedidoEntity pedido;
}
