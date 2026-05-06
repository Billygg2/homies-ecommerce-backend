package com.yavirac.homies.productos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.tallaProducto.TallaProductoDTO;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TallaProductoRepository tallaProductoRepository;

    public ProductoEntity createProductos(ProductoEntity productos, List<TallaProductoDTO> tallas) {
        // Validación de campos
        if (productos.getNombre_producto() == null || productos.getNombre_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (productos.getDescripcion() == null || productos.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (productos.getPrecio_venta() == null) {
            throw new IllegalArgumentException("El precio no puede estar vacío");
        }
        if (productos.getImg_producto() == null || productos.getImg_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen del producto no puede estar vacía");
        }
        if (productos.getCatalogo() == null) {
            throw new IllegalArgumentException("El catalogo no puede estar vacío");
        }
        if (productos.getProveedor() == null) {
            throw new IllegalArgumentException("El proveedor no puede estar vacío");
        }

        ProductoEntity savedProducto = productoRepository.save(productos);

        for (TallaProductoDTO tallaDTO : tallas) {
            TallaProductoEntity talla = new TallaProductoEntity();
            talla.setProducto(savedProducto);
            talla.setTalla(tallaDTO.getTalla());
            talla.setStock(tallaDTO.getStock());
            tallaProductoRepository.save(talla);
        }

        // Guardar producto
        return productoRepository.save(productos);
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> getAllProductos() {
        List<ProductoEntity> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoDTO getProductoById(Long id_producto) {
        ProductoEntity productoEntity = productoRepository.findById(id_producto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertToDTO(productoEntity);
    }

    // Método para convertir ProductoEntity a ProductoDTO
    private ProductoDTO convertToDTO(ProductoEntity productoEntity) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId_producto(productoEntity.getId_producto());
        productoDTO.setNombre_producto(productoEntity.getNombre_producto());
        productoDTO.setDescripcion(productoEntity.getDescripcion());
        productoDTO.setPrecio_venta(productoEntity.getPrecio_venta());
        productoDTO.setImg_producto(productoEntity.getImg_producto());
        productoDTO.setCatalogo(productoEntity.getCatalogo());
        productoDTO.setProveedor(productoEntity.getProveedor());

        // Mapear tallas
        List<TallaProductoDTO> tallasDTO = productoEntity.getTallas().stream().map(
                talla -> {
                    TallaProductoDTO tallaDTO = new TallaProductoDTO();
                    tallaDTO.setId_talla_producto(talla.getId_talla_producto());
                    tallaDTO.setTalla(talla.getTalla());
                    tallaDTO.setStock(talla.getStock());
                    tallaDTO.setProducto(talla.getProducto());
                    return tallaDTO;
                })
                .collect(Collectors.toList());

        productoDTO.setTallas(tallasDTO);
        return productoDTO;
    }

    @Transactional
    public ProductoEntity updateProducto(Long id_producto, ProductoEntity updatedProducto,
            List<TallaProductoDTO> tallas) {
        ProductoEntity existingProducto = productoRepository.findById(id_producto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Validaciones
        if (updatedProducto.getNombre_producto() == null || updatedProducto.getNombre_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (updatedProducto.getDescripcion() == null || updatedProducto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (updatedProducto.getPrecio_venta() == null) {
            throw new IllegalArgumentException("El precio no puede estar vacío");
        }
        if (updatedProducto.getImg_producto() == null || updatedProducto.getImg_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen del producto no puede estar vacía");
        }
        if (updatedProducto.getCatalogo() == null) {
            throw new IllegalArgumentException("El catálogo no puede estar vacío");
        }
        if (updatedProducto.getProveedor() == null) {
            throw new IllegalArgumentException("El proveedor no puede estar vacío");
        }

        // Actualizar los datos del producto
        existingProducto.setNombre_producto(updatedProducto.getNombre_producto());
        existingProducto.setDescripcion(updatedProducto.getDescripcion());
        existingProducto.setPrecio_venta(updatedProducto.getPrecio_venta());
        existingProducto.setImg_producto(updatedProducto.getImg_producto());
        existingProducto.setCatalogo(updatedProducto.getCatalogo());
        existingProducto.setProveedor(updatedProducto.getProveedor());

        // Guardar cambios en el producto
        ProductoEntity savedProducto = productoRepository.save(existingProducto);

        // Actualizar las tallas asociadas al producto
        List<TallaProductoEntity> existingTallas = tallaProductoRepository.findByProducto(existingProducto);

        // Eliminar tallas que ya no están en la lista
        for (TallaProductoEntity talla : existingTallas) {
            if (tallas.stream().noneMatch(t -> t.getId_talla_producto() != null
                    && t.getId_talla_producto().equals(talla.getId_talla_producto()))) {
                tallaProductoRepository.delete(talla);
            }
        }

        // Agregar o actualizar tallas
        for (TallaProductoDTO tallaDTO : tallas) {
            TallaProductoEntity tallaEntity;
            if (tallaDTO.getId_talla_producto() != null) {
                tallaEntity = tallaProductoRepository.findById(tallaDTO.getId_talla_producto())
                        .orElse(new TallaProductoEntity());
            } else {
                tallaEntity = new TallaProductoEntity();
            }
            tallaEntity.setProducto(savedProducto);
            tallaEntity.setTalla(tallaDTO.getTalla());
            tallaEntity.setStock(tallaDTO.getStock());
            tallaProductoRepository.save(tallaEntity);
        }

        return savedProducto;
    }

    @Transactional
    public void deleteProducto(Long id_producto) {
        ProductoEntity producto = productoRepository.findById(id_producto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Eliminar todas las tallas asociadas
        tallaProductoRepository.deleteByProducto(producto);

        // Eliminar el producto
        productoRepository.delete(producto);
    }
}