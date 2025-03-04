package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.OrdemServicoMapper;
import br.com.fiap.lanchonete.business.common.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrdemServicoGateway {
    private static final OrdemServicoMapper MAPPER = OrdemServicoMapper.INSTANCE;
    private final OrdemServicoRepository repository;

    public OrdemServico save(OrdemServico ordemServico) {
        Date now = new Date();
        if (ordemServico.getId() == null) {
            ordemServico.setCreatedAt(now);
        }
        ordemServico.setUpdatedAt(now);
        return MAPPER.toDomain(repository.save(MAPPER.toDto(ordemServico)));
    }

    public void updateStatus(Long id, OrdemServicoStatus status, Date updatedAt) {
        repository.updateStatus(id, status, updatedAt);
    }

    public OrdemServico findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<OrdemServico> findByPedidoId(Long pedidoId) {
        return MAPPER.mapToDomain(repository.findByPedidoId(pedidoId));
    }

    public List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return MAPPER.mapToDomain(repository.findByPedidoIdStatus(pedidoId, status));
    }

    public List<OrdemServico> findByPedidoItemId(Long pedidoItemId) {
        return MAPPER.mapToDomain(repository.findByPedidoItemId(pedidoItemId));
    }
}