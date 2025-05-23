package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.OrdemServicoGateway;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import br.com.fiap.lanchonete.business.core.usecase.OrdemServicoUseCases;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrdemServicoUseCasesImpl implements OrdemServicoUseCases {
    private final OrdemServicoGateway gateway;

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        return gateway.save(ordemServico);
    }

    @Override
    public void updateStatus(Long id, OrdemServicoStatus status) {
        gateway.updateStatus(id, status, new Date());
    }

    @Override
    public List<OrdemServico> findByPedidoId(Long pedidoId) {
        return gateway.findByPedidoId(pedidoId);
    }

    @Override
    public List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return gateway.findByPedidoIdStatus(pedidoId, status);
    }

    @Override
    public List<OrdemServico> findByPedidoItemId(Long pedidoItemId) {
        return gateway.findByPedidoItemId(pedidoItemId);
    }
}