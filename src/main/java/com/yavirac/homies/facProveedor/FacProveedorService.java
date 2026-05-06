package com.yavirac.homies.facProveedor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.proveedor.ProveedorEntity;
import com.yavirac.homies.proveedor.ProveedorRepository;

@Service
public class FacProveedorService {
    @Autowired
    private FacProveedorRepository facProveedorRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public FacProveedorEntity createFacProveedor(String numeroFactura, String imgFactura, Long idProveedor) {
        // Validaciones
        if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura no puede estar vacío");
        }
        if (imgFactura == null || imgFactura.trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen de la factura no puede estar vacía");
        }
        
        ProveedorEntity proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        FacProveedorEntity facProveedor = new FacProveedorEntity();
        facProveedor.setNumero_factura(numeroFactura);
        facProveedor.setImg_factura(imgFactura);
        facProveedor.setProveedor(proveedor);

        return facProveedorRepository.save(facProveedor);
    }

    @Transactional(readOnly = true)
    public List<FacProveedorDTO> getAllFacProveedor() {
        List<FacProveedorEntity> facProveedor = facProveedorRepository.findAll();
        return facProveedor.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FacProveedorDTO getFacProveedorById(Long id_fac_proveedor) {
        FacProveedorEntity facProveedorEntity = facProveedorRepository.findById(id_fac_proveedor)
                .orElseThrow(() -> new RuntimeException("Factura del proveedor no encontrada"));
        return convertToDTO(facProveedorEntity);
    }

    // Método para convertir FacProveedorEntity a FacProveedorDTO
    private FacProveedorDTO convertToDTO(FacProveedorEntity facProveedorEntity) {
        FacProveedorDTO facProveedorDTO = new FacProveedorDTO();
        facProveedorDTO.setId_fac_proveedor(facProveedorEntity.getId_fac_proveedor());
        facProveedorDTO.setNumero_factura(facProveedorEntity.getNumero_factura());
        facProveedorDTO.setImg_factura(facProveedorEntity.getImg_factura());
        facProveedorDTO.setId_proveedor(facProveedorEntity.getProveedor().getId_proveedor());

        return facProveedorDTO;
    }

    public void deleteFacProveedor(Long id_fac_proveedor) {
        FacProveedorEntity facProveedor = facProveedorRepository.findById(id_fac_proveedor)
                .orElseThrow(() -> new RuntimeException("Factura del proveedor no encontrada"));
        facProveedorRepository.delete(facProveedor);
    }

    public FacProveedorEntity updateFacProveedor(Long id_fac_proveedor, FacProveedorEntity facProveedorDetails) {
        FacProveedorEntity facProveedor = facProveedorRepository.findById(id_fac_proveedor)
                .orElseThrow(() -> new RuntimeException("Factura del proveedor no encontrada"));

        facProveedor.setNumero_factura(facProveedorDetails.getNumero_factura());
        facProveedor.setImg_factura(facProveedorDetails.getImg_factura());
        facProveedor.setProveedor(facProveedorDetails.getProveedor());

        return facProveedorRepository.save(facProveedor);
    }
}
