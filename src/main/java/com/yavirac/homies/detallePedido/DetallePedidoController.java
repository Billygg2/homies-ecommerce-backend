package com.yavirac.homies.detallePedido;

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
@RequestMapping("/detallePedido")
@Tag(name = "Controlador de Detalle de Pedido")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos los detalles de pedido")
    public List<DetallePedidoDTO> getAll() {
        return detallePedidoService.getAllDetallePedidos();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta un detalle pedido por el ID")
    public DetallePedidoDTO getById(@PathVariable("id") Long id) {
        return detallePedidoService.getDeatallePedidoById(id);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear y guardar un detalle pedido")
    public DetallePedidoEntity create(@RequestBody CreateDetallePedidoDTO detallePedidoDTO) {
        return detallePedidoService.createDetallePedido(detallePedidoDTO);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un detalle pedido por el ID")
    public DetallePedidoEntity update(@PathVariable("id") Long id, @RequestBody DetallePedidoEntity detallePedidoDetails) {
        return detallePedidoService.updateDetallePedido(id, detallePedidoDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un detalle pedido por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        detallePedidoService.deleteDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
}
