package com.yavirac.homies.facProveedor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yavirac.homies.FirebaseStorage.FirebaseStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/facProveedor")
@Tag(name = "Controlador de FacProveedor")
public class FacProveedorController {
    @Autowired
    private FacProveedorService facProveedorService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear y guardar una factura del proveedor con imagen")
    public ResponseEntity<FacProveedorEntity> create(
            @RequestParam("numero_factura") String numeroFactura,
            @RequestParam("id_proveedor") Long idProveedor,
            @RequestParam("img_factura") MultipartFile imagen) {

        try {
            // Subir imagen a Firebase y obtener la URL
            String imageUrl = firebaseStorageService.uploadProveedorFile(imagen);

            // Guardar factura con el enlace de la imagen
            FacProveedorEntity nuevaFactura = facProveedorService.createFacProveedor(numeroFactura, imageUrl, idProveedor);

            return ResponseEntity.ok(nuevaFactura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta de todos las facturas de los proveedores")
    public List<FacProveedorDTO> getAll() {
        return facProveedorService.getAllFacProveedor();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Consulta un producto por el ID")
    public FacProveedorDTO getById(@PathVariable("id") Long id) {
        return facProveedorService.getFacProveedorById(id);
    }
    

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Edición de los campos de una factura del proveedor por el ID")
    public FacProveedorEntity update(@PathVariable("id") Long id, @RequestBody FacProveedorEntity facProveedorDetails) {
        return facProveedorService.updateFacProveedor(id, facProveedorDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar una factura del proveedor por el ID")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        facProveedorService.deleteFacProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
