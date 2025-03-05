package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.List;

public interface PedidoRepository {
    PedidoDto save(PedidoDto dto);
    List<PedidoDto> findAll();
    List<PedidoDto> findByStatus(PedidoStatus status);
    PedidoDto findById(Long id);
    List<PedidoDto> findByClienteId(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
    List<PedidoDto> findAllOrdered();
    List<PedidoDto> findByStatusOrderByUpdatedAtDesc(PedidoStatus status);
}