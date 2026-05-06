package com.yavirac.homies.facProveedor;
import lombok.Data;

@Data
public class FacProveedorDTO {
    private Long id_fac_proveedor;
    private String numero_factura;
    private String img_factura;
    private Long id_proveedor;
}
