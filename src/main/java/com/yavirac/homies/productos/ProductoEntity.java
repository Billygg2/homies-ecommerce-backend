package com.yavirac.homies.productos;

import java.math.BigDecimal;
import java.util.Set;

import com.yavirac.homies.catalogo.CatalogoEntity;
import com.yavirac.homies.proveedor.ProveedorEntity;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;

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
@Table(name = "productos")
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre_producto;

    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precio_venta;

    @Column(name = "imagen", length = 255, nullable = false)
    private String img_producto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private Set<TallaProductoEntity> tallas;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_catalogo", nullable = false)
    private CatalogoEntity catalogo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorEntity proveedor;
}