package com.yavirac.homies.User;

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

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "Controlador del Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos los usuarios")
    public List<User> getAll() {
        return usuarioService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Crear y guardar un usuario")
    public User create(@RequestBody User usuario) {
        return usuarioService.createUser(usuario);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta un usuario por el ID")
    public User getById(@PathVariable("id") Long id) {
        return usuarioService.getUserById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un usuario por el ID")
    public User update(@PathVariable("id") Long id, @RequestBody User usuarioDetails) {
        return usuarioService.updateUser(id, usuarioDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un usuario por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
