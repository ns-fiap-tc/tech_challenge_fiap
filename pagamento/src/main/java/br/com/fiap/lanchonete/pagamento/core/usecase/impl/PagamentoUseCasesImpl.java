package br.com.fiap.lanchonete.pagamento.core.usecase.impl;

import br.com.fiap.lanchonete.pagamento.adapter.gateway.PagamentoGateway;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import br.com.fiap.lanchonete.pagamento.core.usecase.PagamentoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PagamentoUseCasesImpl implements PagamentoUseCases {
    private final PagamentoGateway gateway;

    @Override
    public Pagamento save(Pagamento pagamento) {
        return gateway.save(pagamento);
    }

    @Override
    public Pagamento findById(String id) {
        return gateway.findById(id);
    }

    @Override
    public List<Pagamento> findAll() {
        return gateway.findAll();
    }

    @Override
    public void deleteById(String id) {
        gateway.deleteById(id);
    }

    @Override
    public void updateStatus(Long pedidoId, PagamentoStatus status) {
        Pagamento pagamento = gateway.findByPedidoId(pedidoId);
        if (pagamento.getStatus() != PagamentoStatus.CONFIRMADO) {
            gateway.updateStatus(pagamento.getPedidoId(), status);
        }
    }

    @Override
    public Pagamento findByPedidoId(Long pedidoId) {
        return gateway.findByPedidoId(pedidoId);
    }
}