package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.ClienteMapper;
import br.com.fiap.lanchonete.adapter.output.persistence.mapper.OrdemServicoMapper;
import br.com.fiap.lanchonete.domain.model.OrdemServico;
import br.com.fiap.lanchonete.domain.model.OrdemServicoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.OrdemServicoRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {
    private static final OrdemServicoMapper MAPPER = OrdemServicoMapper.INSTANCE;
    private final OrdemServicoJpaRepository repository;

    @Override
    public OrdemServico save(OrdemServico ordemServico) {
        Date now = new Date();
        if (ordemServico.getId() == null) {
            ordemServico.setCreatedAt(now);
        }
        ordemServico.setUpdatedAt(now);
        return MAPPER.toDomain(
                repository.save(
                        MAPPER.toEntity(ordemServico)));
    }

    @Override
    public void updateStatus(Long id, OrdemServicoStatus status, Date updatedAt) {
        repository.updateStatus(id, status, updatedAt);
    }

    @Override
    public OrdemServico findById(Long id) {
        return MAPPER.toDomain(
                repository.findById(id).orElse(null));
    }

    @Override
    public List<OrdemServico> findByPedidoId(Long pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }

    @Override
    public List<OrdemServico> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return repository.findByPedidoIdStatus(pedidoId, status)
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }

    @Override
    public List<OrdemServico> findByPedidoItemId(Long pedidoItemId) {
        return repository.findByPedidoItemId(pedidoItemId)
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }
}
