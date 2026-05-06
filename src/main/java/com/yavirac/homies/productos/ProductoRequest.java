package com.yavirac.homies.productos;

import java.util.List;

import com.yavirac.homies.tallaProducto.TallaProductoDTO;

public class ProductoRequest {
    private ProductoEntity producto;
    private List<TallaProductoDTO> tallas;

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public List<TallaProductoDTO> getTallas() {
        return tallas;
    }

    public void setTallas(List<TallaProductoDTO> tallas) {
        this.tallas = tallas;
    }
}
