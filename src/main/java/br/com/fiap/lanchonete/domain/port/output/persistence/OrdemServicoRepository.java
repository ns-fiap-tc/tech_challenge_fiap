package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.model.OrdemServicoStatus;
import java.util.Date;
import java.util.List;

public interface OrdemServicoRepository {
    OrdemServico save(OrdemServico ordemServico);
    void updateStatus(Long id, OrdemServicoStatus status, Date updatedAt);
    OrdemServico findById(Long id);
    List<OrdemServico> findByPedidoId(Long pedidoId);
    List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status);
    List<OrdemServico> findByPedidoItemId(Long pedidoItemId);
}
