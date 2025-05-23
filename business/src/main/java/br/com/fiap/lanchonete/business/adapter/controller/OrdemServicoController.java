package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.OrdemServicoGateway;
import br.com.fiap.lanchonete.business.common.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import br.com.fiap.lanchonete.business.core.usecase.OrdemServicoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.OrdemServicoUseCasesImpl;
import java.util.List;

/**
 * Classe utilizada apenas dentro do pacote business.
 */
public class OrdemServicoController {
    private final OrdemServicoUseCases useCase;

    public OrdemServicoController(OrdemServicoRepository ordemServicoRepository) {
        useCase = new OrdemServicoUseCasesImpl(new OrdemServicoGateway(ordemServicoRepository));
    }

    OrdemServicoUseCases getUseCases() {
        return this.useCase;
    }

    public OrdemServico save(OrdemServico ordemServico) {
        return useCase.save(ordemServico);
    }

    public void updateStatus(Long id, OrdemServicoStatus status) {
        useCase.updateStatus(id, status);
    }

    public List<OrdemServico> findByPedidoId(Long pedidoId) {
        return useCase.findByPedidoId(pedidoId);
    }

    public List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return useCase.findByPedidoIdStatus(pedidoId, status);
    }

    public List<OrdemServico> findByPedidoItemId(Long pedidoItemId) {
        return useCase.findByPedidoItemId(pedidoItemId);
    }
}