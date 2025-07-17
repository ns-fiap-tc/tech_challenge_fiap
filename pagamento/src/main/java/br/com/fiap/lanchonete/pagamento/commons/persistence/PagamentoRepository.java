package br.com.fiap.lanchonete.pagamento.commons.persistence;

import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import java.util.List;

public interface PagamentoRepository {
    PagamentoDto save(PagamentoDto dto);
    PagamentoDto findById(String id);
    List<PagamentoDto> findAll();
    void deleteById(String id);
    void updateStatus(Long pedidoId, PagamentoStatus status);
    PagamentoDto findByPedidoId(Long pedidoId);
}