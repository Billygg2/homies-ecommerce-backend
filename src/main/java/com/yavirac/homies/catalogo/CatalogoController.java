package com.yavirac.homies.catalogo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yavirac.homies.productos.ProductoEntity;
import com.yavirac.homies.productos.ProductoRepository;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/catalogo")
@Tag(name = "Controlador de Catálogos")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos los catalogos")
    public List<CatalogoEntity> getAll() {
        return catalogoService.getAllCatalogos();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CatalogoEntity crearCatalogo(@RequestBody CatalogoEntity catalogo) {
        return catalogoService.crearCatalogo(catalogo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public CatalogoEntity getById(@PathVariable("id") Long id) {
        return catalogoService.getCatalogoById(id);
    }
    
    @GetMapping("/{id}/productos")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Obtener nombres de productos asociados a un catálogo")
    public List<String> getNombreProductosByCatalogo(@PathVariable("id") Long id) {
        CatalogoEntity catalogo = catalogoService.getCatalogoById(id);
        return productoRepository.findByCatalogo(catalogo)
                .stream()
                .map(ProductoEntity::getNombre_producto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un catálogo por el ID")
    public CatalogoEntity update(@PathVariable("id") Long id, @RequestBody CatalogoEntity catalogoDetails) {
        return catalogoService.updateCatalogo(id, catalogoDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un catálogo por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        catalogoService.deleteCatalogo(id);
        return ResponseEntity.noContent().build();
    }
}