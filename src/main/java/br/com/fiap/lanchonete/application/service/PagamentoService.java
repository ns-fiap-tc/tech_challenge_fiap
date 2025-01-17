package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Pagamento;
import br.com.fiap.lanchonete.domain.port.output.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.domain.usecase.PagamentoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PagamentoService implements PagamentoUseCases {
    private final PagamentoRepository repository;

    public boolean pagar() {
        return true;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    @Override
    public Pagamento findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pagamento> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
