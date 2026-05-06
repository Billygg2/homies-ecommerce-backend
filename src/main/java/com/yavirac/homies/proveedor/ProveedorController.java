package com.yavirac.homies.proveedor;

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
@RequestMapping("/proveedor")
@Tag(name = "Controlador de Proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos los proveedores")
    public List<ProveedorEntity> getAll() {
        return proveedorService.getAllProveedores();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear y guardar un proveedor")
    public ProveedorEntity create(@RequestBody ProveedorEntity proveedor) {
        return proveedorService.createProveedores(proveedor);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta un proveedor por el ID")
    public ProveedorEntity getById(@PathVariable("id") Long id) {
        return proveedorService.getProveedorById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un proveedor por el ID")
    public ProveedorEntity update(@PathVariable("id") Long id, @RequestBody ProveedorEntity proveedorDetails) {
        return proveedorService.updateProveedor(id, proveedorDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un proveedor por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        proveedorService.deleteProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
