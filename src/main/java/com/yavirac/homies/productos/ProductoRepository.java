package com.yavirac.homies.productos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yavirac.homies.catalogo.CatalogoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    List<ProductoEntity> findByCatalogo(CatalogoEntity catalogo);
    long countByCatalogo(CatalogoEntity catalogo);
}