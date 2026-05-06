package com.yavirac.homies.proveedor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public ProveedorEntity createProveedores(ProveedorEntity proveedor) {
        // Validación de campos
        if (proveedor.getNombres() == null || proveedor.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío");
        }
        if (proveedor.getCedula_ruc() == null || proveedor.getCedula_ruc().trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula o ruc no puede estar vacía");
        }
        if (proveedor.getTelefono() == null || proveedor.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de telefono no puede estar vacío");
        }
        if (proveedor.getDireccion() == null || proveedor.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        // Guardar proveedor
        return proveedorRepository.save(proveedor);
    }

    public List<ProveedorEntity> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    public ProveedorEntity getProveedorById(Long id_proveedor) {
        return proveedorRepository.findById(id_proveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public void deleteProveedor(Long id_proveedor) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedorRepository.delete(proveedor);
    }

    public ProveedorEntity updateProveedor(Long id_proveedor, ProveedorEntity proveedorDetails) {
        ProveedorEntity proveedor = proveedorRepository.findById(id_proveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombres(proveedorDetails.getNombres());
        proveedor.setCedula_ruc(proveedorDetails.getCedula_ruc());
        proveedor.setTelefono(proveedorDetails.getTelefono());
        proveedor.setDireccion(proveedorDetails.getDireccion());

        return proveedorRepository.save(proveedor);
    }
}
