package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Pagamento;
import java.util.List;

public interface PagamentoRepository {
    Pagamento save(Pagamento pagamento);
    Pagamento findById(Long id);
    List<Pagamento> findAll();
    void deleteById(Long id);
}
