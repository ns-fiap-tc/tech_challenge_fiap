package br.com.fiap.lanchonete.application.adapter.persistence.repository;

import br.com.fiap.lanchonete.application.adapter.persistence.mapper.OrdemServicoMapper;
import br.com.fiap.lanchonete.business.common.dto.OrdemServicoDto;
import br.com.fiap.lanchonete.business.common.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
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
    public OrdemServicoDto save(OrdemServicoDto dto) {
/*
        Date now = new Date();
        if (dto.getId() == null) {
            dto.setCreatedAt(now);
        }
        dto.setUpdatedAt(now);
 */
        return MAPPER.toDto(
                repository.save(
                        MAPPER.toEntity(dto)));
    }

    @Override
    public void updateStatus(Long id, OrdemServicoStatus status, Date updatedAt) {
        repository.updateStatus(id, status, updatedAt);
    }

    @Override
    public OrdemServicoDto findById(Long id) {
        return MAPPER.toDto(
                repository.findById(id).orElse(null));
    }

    @Override
    public List<OrdemServicoDto> findByPedidoId(Long pedidoId) {
        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public List<OrdemServicoDto> findByPedidoIdStatus(Long pedidoId, OrdemServicoStatus status) {
        return repository.findByPedidoIdStatus(pedidoId, status)
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public List<OrdemServicoDto> findByPedidoItemId(Long pedidoItemId) {
        return repository.findByPedidoItemId(pedidoItemId)
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }
}