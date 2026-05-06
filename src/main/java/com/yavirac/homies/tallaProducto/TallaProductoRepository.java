package com.yavirac.homies.tallaProducto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.productos.ProductoEntity;

public interface TallaProductoRepository extends JpaRepository<TallaProductoEntity, Long> {
    List<TallaProductoEntity> findByProducto(ProductoEntity producto);

    Optional<TallaProductoEntity> findByProductoAndTalla(ProductoEntity producto, String talla);

    // Método para eliminar todas las tallas de un producto
    @Transactional
    void deleteByProducto(ProductoEntity producto);
}
