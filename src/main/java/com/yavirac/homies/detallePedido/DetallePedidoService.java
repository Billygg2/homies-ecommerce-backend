package com.yavirac.homies.detallePedido;

import java.util.List;
import java.util.stream.Collectors;
import java.lang.System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.pedido.PedidoEntity;
import com.yavirac.homies.pedido.PedidoRepository;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoRepository;

@Service
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private TallaProductoRepository tallaProductoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public DetallePedidoEntity createDetallePedido(CreateDetallePedidoDTO detallePedidoDTO) {
        // Validar los IDs de producto y pedido
        TallaProductoEntity tallaProducto = tallaProductoRepository.findById(detallePedidoDTO.getTallaProducto())
                .orElseThrow(
                        () -> new RuntimeException("Producto no encontrado con ID: " + detallePedidoDTO.getTallaProducto()));

        System.out.println("Id del pedido " + detallePedidoDTO.getPedido());
        
        PedidoEntity pedido = pedidoRepository.findById(detallePedidoDTO.getPedido())
                .orElseThrow(
                        () -> new RuntimeException("Pedido no encontrado con ID: " + detallePedidoDTO.getPedido()));

        // Crear la entidad de DetallePedido
        DetallePedidoEntity detallePedido = new DetallePedidoEntity();
        detallePedido.setIva(detallePedidoDTO.getIva());
        detallePedido.setSubtotal(detallePedidoDTO.getSubtotal());
        detallePedido.setTotal(detallePedidoDTO.getTotal());
        detallePedido.setCantidad(detallePedidoDTO.getCantidad());
        detallePedido.setTalla(detallePedidoDTO.getTalla());
        detallePedido.setTallaProducto(tallaProducto);
        detallePedido.setPedido(pedido);

        // Guardar y devolver la entidad
        return detallePedidoRepository.save(detallePedido);
    }

    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> getAllDetallePedidos() {
        List<DetallePedidoEntity> detallePedido = detallePedidoRepository.findAll();
        return detallePedido.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetallePedidoDTO getDeatallePedidoById(Long id_detalle_pedido) {
        DetallePedidoEntity detallePedido = detallePedidoRepository.findById(id_detalle_pedido)
                .orElseThrow(() -> new RuntimeException("Detalle del pedido no encontrado"));
        return convertToDTO(detallePedido);
    }

    // Método para convertir DetallePedidoEntity a DetallePedidoDTO
    private DetallePedidoDTO convertToDTO(DetallePedidoEntity detallePedidoEntity) {
        DetallePedidoDTO detallePedidoDTO = new DetallePedidoDTO();
        detallePedidoDTO.setId_detalle_pedido(detallePedidoEntity.getId_detalle_pedido());
        detallePedidoDTO.setIva(detallePedidoEntity.getIva());
        detallePedidoDTO.setSubtotal(detallePedidoEntity.getSubtotal());
        detallePedidoDTO.setTotal(detallePedidoEntity.getTotal());
        detallePedidoDTO.setCantidad(detallePedidoEntity.getCantidad());
        detallePedidoDTO.setTalla(detallePedidoEntity.getTalla());
        detallePedidoDTO.setTallaProducto(detallePedidoEntity.getTallaProducto());
        detallePedidoDTO.setPedido(detallePedidoEntity.getPedido());

        return detallePedidoDTO;
    }

    public void deleteDetallePedido(Long id_detalle_pedido) {
        DetallePedidoEntity detallePedido = detallePedidoRepository.findById(id_detalle_pedido)
                .orElseThrow(() -> new RuntimeException("Detalle del pedido no encontrado"));
        detallePedidoRepository.delete(detallePedido);
    }

    public DetallePedidoEntity updateDetallePedido(Long id_detalle_pedido, DetallePedidoEntity detallePedidoDetails) {
        DetallePedidoEntity detallePedido = detallePedidoRepository.findById(id_detalle_pedido)
                .orElseThrow(() -> new RuntimeException("Detalle del pedido no encontrado"));

        detallePedido.setIva(detallePedidoDetails.getIva());
        detallePedido.setSubtotal(detallePedidoDetails.getSubtotal());
        detallePedido.setTotal(detallePedidoDetails.getTotal());
        detallePedido.setCantidad(detallePedidoDetails.getCantidad());
        detallePedido.setTalla(detallePedidoDetails.getTalla());
        detallePedido.setTallaProducto(detallePedidoDetails.getTallaProducto());
        detallePedido.setPedido(detallePedidoDetails.getPedido());

        return detallePedidoRepository.save(detallePedido);
    }
}
