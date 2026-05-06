package com.yavirac.homies.tallaProducto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tallaProducto")
@Tag(name = "Controlador de las tallas de los productos")
public class TallaProductoController {
    @Autowired
    private TallaProductoService tallaProductoService;

    @GetMapping
    @Operation(summary = "Consulta de todos las tallas de los productos")
    public List<TallaProductoDTO> getAll() {
        return tallaProductoService.getAllPedido();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de una talla pedido por ID")
    public TallaProductoDTO getById(@PathVariable("id") Long id) {
        return tallaProductoService.getPedidoById(id);
    }
}
