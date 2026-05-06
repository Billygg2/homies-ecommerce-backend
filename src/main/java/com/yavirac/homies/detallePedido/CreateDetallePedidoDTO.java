package com.yavirac.homies.detallePedido;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateDetallePedidoDTO {
    private BigDecimal iva;
    private BigDecimal subtotal;
    private BigDecimal total;
    private Integer cantidad;
    private String talla;
    private Long tallaProducto;
    private Long pedido;
}
