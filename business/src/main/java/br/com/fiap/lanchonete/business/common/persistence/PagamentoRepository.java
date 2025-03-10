package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import java.util.List;

public interface PagamentoRepository {
    PagamentoDto save(PagamentoDto dto);
    PagamentoDto findById(Long id);
    List<PagamentoDto> findAll();
    void deleteById(Long id);
    void updateStatus(Long id, PagamentoStatus status);
    PagamentoDto findByPedidoId(Long pedidoId);
}