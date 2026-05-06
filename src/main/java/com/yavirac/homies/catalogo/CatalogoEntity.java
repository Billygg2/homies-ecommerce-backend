package com.yavirac.homies.catalogo;


import java.util.Set;

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
@Table(name = "catalogo")
public class CatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_catalogo;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombreCatalogo;
    
    @OneToMany(mappedBy = "catalogo")
    private Set<ProductoEntity> productos;
}