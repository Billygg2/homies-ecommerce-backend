package com.yavirac.homies.facProveedor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yavirac.homies.proveedor.ProveedorEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "factura_proveedor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_fac_proveedor")
public class FacProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fac_proveedor;

    @Column(nullable = false)
    private String numero_factura;

    @Column(name = "imagen_factura", nullable = false)
    private String img_factura;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorEntity proveedor;
}
