package com.yavirac.homies.tallaProducto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TallaProductoService {
    @Autowired
    private TallaProductoRepository tallaProductoRepository;

    @Transactional
    public List<TallaProductoDTO> getAllPedido() {
        List<TallaProductoEntity> tallas = tallaProductoRepository.findAll();
        return tallas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TallaProductoDTO getPedidoById(Long id_talla_producto) {
        TallaProductoEntity tallasEntity = tallaProductoRepository.findById(id_talla_producto)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return convertToDTO(tallasEntity);
    }

    private TallaProductoDTO convertToDTO(TallaProductoEntity tallaProductoEntity) {
        TallaProductoDTO tallaProductoDTO = new TallaProductoDTO();
        tallaProductoDTO.setId_talla_producto(tallaProductoEntity.getId_talla_producto());
        tallaProductoDTO.setTalla(tallaProductoEntity.getTalla());
        tallaProductoDTO.setStock(tallaProductoEntity.getStock());
        tallaProductoDTO.setProducto(tallaProductoEntity.getProducto());
        return tallaProductoDTO;
    }
}
