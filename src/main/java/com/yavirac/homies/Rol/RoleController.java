package com.yavirac.homies.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/roles")
@Tag(name = "Controlador del Rol")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Consulta de todos los roles")
    public List<Role> getAll() {
        return roleService.getAllRole();
    }

    @PostMapping
    @Operation(summary = "Crear y guardar un rol")
    public Role create(@RequestBody Role rol) {
        return roleService.createRol(rol);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta un rol por el ID")
    public Role getById(@PathVariable("id") Long id) {
        return roleService.getRolById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Edición del rol por el ID")
    public Role update(@PathVariable("id") Long id, @RequestBody Role rolDetails) {
        return roleService.updateRol(id, rolDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un rol por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        roleService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}