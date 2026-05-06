package com.yavirac.homies.pedido;

import com.yavirac.homies.User.User;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id_pedido;
    private Timestamp pedido_fecha;
    private Boolean estado;
    private String direccion;
    // Para la factura del usuario
    private Timestamp fecha_factura;
    private BigDecimal total_factura;
    private String img_voucher;
    // Asociar el usuario al pedido
    private User usuario;
}
