package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import java.util.List;

public interface PagamentoUseCases {
    boolean pagar();
    Pagamento save(Pagamento pagamento);
    Pagamento findById(Long id);
    List<Pagamento> findAll();
    void deleteById(Long id);
}