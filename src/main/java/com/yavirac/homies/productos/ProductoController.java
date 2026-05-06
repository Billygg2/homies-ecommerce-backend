package com.yavirac.homies.productos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Controlador de Productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Consulta de todos los productos")
    public List<ProductoDTO> getAll() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta un producto por el ID")
    public ProductoDTO getById(@PathVariable("id") Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear y guardar un producto")
    public ProductoEntity create(@RequestBody ProductoRequest productos) {
        return productoService.createProductos(productos.getProducto(), productos.getTallas());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un producto por el ID")
    public ProductoEntity update(@PathVariable("id") Long id, @RequestBody ProductoRequest productoDetails) {
        return productoService.updateProducto(id, productoDetails.getProducto(), productoDetails.getTallas());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un producto por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}