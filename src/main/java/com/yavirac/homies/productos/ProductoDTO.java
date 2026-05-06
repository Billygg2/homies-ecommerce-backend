package com.yavirac.homies.productos;

import java.math.BigDecimal;
import java.util.List;

import com.yavirac.homies.catalogo.CatalogoEntity;
import com.yavirac.homies.proveedor.ProveedorEntity;
import com.yavirac.homies.tallaProducto.TallaProductoDTO;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id_producto;
    private String nombre_producto;
    private String descripcion;
    private BigDecimal precio_venta;
    private String img_producto;
    private List<TallaProductoDTO> tallas;
    private CatalogoEntity catalogo;
    private ProveedorEntity proveedor; 
}
