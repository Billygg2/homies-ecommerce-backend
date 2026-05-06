package com.yavirac.homies.proveedor;

import java.util.Set;

import com.yavirac.homies.facProveedor.FacProveedorEntity;
import com.yavirac.homies.productos.ProductoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedor")
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proveedor;

    @Column(name = "nombres_contacto", length = 50, nullable = false)
    private String nombres;
    
    @Column(name = "cedula_ruc", length = 15, nullable = false)
    private String cedula_ruc;
    
    @Column(name = "telefono_proveedor", length = 15, nullable = false)
    private String telefono;
    
    @Column(name = "direccion_proveedor", length = 100, nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "proveedor")
    private Set<ProductoEntity> productos;
    
    @OneToMany(mappedBy = "proveedor")
    private Set<FacProveedorEntity> facProveedor;
}
