package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.PagamentoGateway;
import br.com.fiap.lanchonete.business.common.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.usecase.PagamentoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.PagamentoUseCasesImpl;
import java.util.List;

public class PagamentoController {
    private final PagamentoUseCases useCase;
    //private final PagamentoPresenter presenter;

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        useCase = new PagamentoUseCasesImpl(new PagamentoGateway(pagamentoRepository));
    }

    PagamentoUseCases getUseCases() {
        return this.useCase;
    }

    public boolean pagar() {
        return useCase.pagar();
    }

    public Pagamento save(Pagamento pagamento) {
        return useCase.save(pagamento);
    }

    public Pagamento findById(Long id) {
        return useCase.findById(id);
    }

    public List<Pagamento> findAll() {
        return useCase.findAll();
    }

    public void deleteById(Long id) {
        useCase.deleteById(id);
    }
}