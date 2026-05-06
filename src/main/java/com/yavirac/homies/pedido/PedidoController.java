package com.yavirac.homies.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yavirac.homies.FirebaseStorage.FirebaseStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedido")
@Tag(name = "Controlador del Pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos los pedidos")
    public List<PedidoDTO> getAll() {
        return pedidoService.getAllPedido();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Crear y guardar un pedido")
    public PedidoEntity create(@RequestBody PedidoEntity pedido) {
        return pedidoService.createPedido(pedido);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de un pedido por ID")
    public PedidoDTO getById(@PathVariable("id") Long id) {
        return pedidoService.getPedidoById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de un pedido por ID")
    public PedidoEntity update(@PathVariable("id") Long id, @RequestBody PedidoEntity pedidoDetails) {
        return pedidoService.updatePedido(id, pedidoDetails);
    }

    @PatchMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Edición de los campos de un pedido por ID")
    public ResponseEntity<PedidoEntity> updateVoucher(
            @PathVariable("id") Long id,
            @RequestPart(value = "direccion", required = false) String direccion,
            @RequestPart(value = "voucher", required = false) MultipartFile voucherImage) {

        try {
            // Buscar el pedido existente
            PedidoEntity pedido = pedidoService.getPedidoByIdEntity(id);

            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Actualizar solo los campos proporcionados
            if (direccion != null) {
                pedido.setDireccion(direccion);
            }

            // Si se proporciona una imagen, subirla y actualizar la URL
            if (voucherImage != null && !voucherImage.isEmpty()) {
                String imageUrl = firebaseStorageService.uploadProductoFile(voucherImage);
                pedido.setImg_voucher(imageUrl);
            }

            // Guardar los cambios
            PedidoEntity updatedPedido = pedidoService.updatePedido(id, pedido);

            return ResponseEntity.ok(updatedPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un pedido y sus relaciones por ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
