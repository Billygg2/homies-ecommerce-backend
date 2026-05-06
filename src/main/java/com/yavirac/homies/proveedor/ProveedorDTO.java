package com.yavirac.homies.proveedor;

import lombok.Data;

@Data
public class ProveedorDTO {
    private Long id_proveedor;
    private String nombres;
    private String cedula_ruc;
    private String telefono;
    private String direccion;
}
