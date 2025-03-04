package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.OrdemServicoDto;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.Date;
import java.util.List;

public interface OrdemServicoRepository {
    OrdemServicoDto save(OrdemServicoDto dto);
    void updateStatus(Long id, OrdemServicoStatus status, Date updatedAt);
    OrdemServicoDto findById(Long id);
    List<OrdemServicoDto> findByPedidoId(Long pedidoId);
    List<OrdemServicoDto> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status);
    List<OrdemServicoDto> findByPedidoItemId(Long pedidoItemId);
}