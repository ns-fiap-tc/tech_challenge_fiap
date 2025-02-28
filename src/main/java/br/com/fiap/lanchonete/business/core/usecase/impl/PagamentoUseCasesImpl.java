package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.PagamentoGateway;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.usecase.PagamentoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PagamentoUseCasesImpl implements PagamentoUseCases {
    private final PagamentoGateway gateway;

    public boolean pagar() {
        return true;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        return gateway.save(pagamento);
    }

    @Override
    public Pagamento findById(Long id) {
        return gateway.findById(id);
    }

    @Override
    public List<Pagamento> findAll() {
        return gateway.findAll();
    }

    @Override
    public void deleteById(Long id) {
        gateway.deleteById(id);
    }
}