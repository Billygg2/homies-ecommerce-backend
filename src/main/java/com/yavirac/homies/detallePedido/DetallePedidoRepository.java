package com.yavirac.homies.detallePedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yavirac.homies.pedido.PedidoEntity;
import com.yavirac.homies.tallaProducto.TallaProductoEntity;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long> {
    List<DetallePedidoEntity> findByTallaProducto(TallaProductoEntity tallaProducto);

    List<DetallePedidoEntity> findByPedido(PedidoEntity id_pedido);
}
