package com.yavirac.homies.tallaProducto;

import com.yavirac.homies.productos.ProductoEntity;

import lombok.Data;

@Data
public class TallaProductoDTO {
    private Long id_talla_producto;
    private String talla;
    private Integer stock;
    private ProductoEntity producto;
}
