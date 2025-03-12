package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import java.util.List;

public interface PagamentoUseCases {
    Pagamento save(Pagamento pagamento);
    Pagamento findById(Long id);
    List<Pagamento> findAll();
    void deleteById(Long id);
    void updateStatus(Long id, PagamentoStatus status);
    Pagamento findByPedidoId(Long pedidoId);
}