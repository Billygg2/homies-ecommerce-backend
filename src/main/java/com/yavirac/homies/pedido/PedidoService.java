package com.yavirac.homies.pedido;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yavirac.homies.detallePedido.DetallePedidoEntity;
import com.yavirac.homies.detallePedido.DetallePedidoRepository;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TallaProductoRepository tallaProductoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional
    public PedidoEntity createPedido(PedidoEntity pedido) {
        // Validación de campos obligatorios
        if (pedido.getPedido_fecha() == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede estar vacía");
        }
        if (pedido.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
        // Guardar el pedido en la base de datos
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public List<PedidoDTO> getAllPedido() {
        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PedidoDTO convertToDTO(PedidoEntity pedidoEntity) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId_pedido(pedidoEntity.getId_pedido());
        pedidoDTO.setPedido_fecha(pedidoEntity.getPedido_fecha());
        pedidoDTO.setEstado(pedidoEntity.getEstado());
        pedidoDTO.setDireccion(pedidoEntity.getDireccion());
        pedidoDTO.setFecha_factura(pedidoEntity.getFecha_factura());
        pedidoDTO.setTotal_factura(pedidoEntity.getTotal_factura());
        pedidoDTO.setImg_voucher(pedidoEntity.getImg_voucher());
        pedidoDTO.setUsuario(pedidoEntity.getUsuario());

        return pedidoDTO;
    }

    @Transactional(readOnly = true)
    public PedidoDTO getPedidoById(Long id_pedido) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return convertToDTO(pedidoEntity);
    }

    @Transactional
    public PedidoEntity confirmarPedido(Long id_pedido) {
        // Obtener el pedido completo con sus detalles
        PedidoEntity pedido = pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id_pedido));

        // Verificar que el pedido no esté ya confirmado
        if (Boolean.TRUE.equals(pedido.getEstado())) {
            throw new IllegalStateException("El pedido con ID " + id_pedido + " ya está confirmado");
        }

        // Cargar explícitamente todos los detalles del pedido usando el método correcto
        List<DetallePedidoEntity> detalles = detallePedidoRepository.findByPedido(pedido);

        if (detalles.isEmpty()) {
            throw new IllegalStateException("El pedido con ID " + id_pedido + " no tiene detalles");
        }

        // Calcular el total de la factura
        BigDecimal totalFactura = BigDecimal.ZERO;

        // Verificar stock y actualizar
        for (DetallePedidoEntity detalle : detalles) {
            // Obtener la referencia actualizada de TallaProducto
            TallaProductoEntity tallaProducto = tallaProductoRepository
                    .findById(detalle.getTallaProducto().getId_talla_producto())
                    .orElseThrow(() -> new RuntimeException("Talla de producto no encontrada con ID: " +
                            detalle.getTallaProducto().getId_talla_producto()));

            // Verificar si hay suficiente stock
            if (tallaProducto.getStock() < detalle.getCantidad()) {
                throw new IllegalStateException(
                        "Stock insuficiente para el producto: " +
                                tallaProducto.getProducto().getNombre_producto() +
                                " talla: " + tallaProducto.getTalla() +
                                ". Stock disponible: " + tallaProducto.getStock() +
                                ", Cantidad solicitada: " + detalle.getCantidad());
            }

            // Actualizar el stock
            int nuevoStock = tallaProducto.getStock() - detalle.getCantidad();
            tallaProducto.setStock(nuevoStock);
            tallaProductoRepository.save(tallaProducto);

            // Sumar al total de la factura
            totalFactura = totalFactura.add(detalle.getTotal());
        }

        // Actualizar el pedido
        pedido.setEstado(true);
        pedido.setFecha_factura(new Timestamp(System.currentTimeMillis()));
        pedido.setTotal_factura(totalFactura);

        // Guardar el pedido actualizado
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public PedidoEntity getPedidoByIdEntity(Long id_pedido) {
        return pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Transactional
    public PedidoEntity updatePedido(Long id_pedido, PedidoEntity pedidoDetails) {
        PedidoEntity pedido = pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Si el estado está cambiando de false a true, usar el método de confirmación
        if (Boolean.FALSE.equals(pedido.getEstado()) && Boolean.TRUE.equals(pedidoDetails.getEstado())) {
            return confirmarPedido(id_pedido);
        }

        // Si no está cambiando el estado o está cambiando de true a false, actualizar
        // normalmente
        pedido.setPedido_fecha(pedidoDetails.getPedido_fecha());
        pedido.setEstado(pedidoDetails.getEstado());
        pedido.setDireccion(pedidoDetails.getDireccion());
        pedido.setFecha_factura(pedidoDetails.getFecha_factura());
        pedido.setTotal_factura(pedidoDetails.getTotal_factura());
        pedido.setImg_voucher(pedidoDetails.getImg_voucher());
        pedido.setUsuario(pedidoDetails.getUsuario());

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletePedido(Long id_pedido) {
        PedidoEntity pedido = pedidoRepository.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }
}
