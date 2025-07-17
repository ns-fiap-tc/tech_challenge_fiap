package br.com.fiap.lanchonete.pagamento.core.usecase;

import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import java.util.List;

public interface PagamentoUseCases {
    Pagamento save(Pagamento pagamento);
    Pagamento findById(String id);
    List<Pagamento> findAll();
    void deleteById(String id);
    void updateStatus(Long id, PagamentoStatus status);
    Pagamento findByPedidoId(Long pedidoId);
}