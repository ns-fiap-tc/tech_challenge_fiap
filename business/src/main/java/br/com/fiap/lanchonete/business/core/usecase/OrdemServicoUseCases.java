package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.List;

public interface OrdemServicoUseCases {
    OrdemServico save(OrdemServico ordemServico);
    void updateStatus(Long id, OrdemServicoStatus status);
    List<OrdemServico> findByPedidoId(Long pedidoId);
    List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status);
    List<OrdemServico> findByPedidoItemId(Long pedidoItemId);
}