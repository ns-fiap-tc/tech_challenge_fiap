package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoRepositoryImpl implements PedidoRepository {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoJpaRepository repository;

    @Override
    public PedidoDto save(PedidoDto dto) {
        return MAPPER.toDto(
                this.repository.save(MAPPER.toEntity(dto)));
    }

    @Override
    public List<PedidoDto> findAll() {
        return MAPPER.map(repository.findAllOrdered());
    }

    @Override
    public List<PedidoDto> findByStatus(PedidoStatus status) {
        return MAPPER.map(repository.findByStatusOrderByUpdatedAtDesc(status));
    }

    @Override
    public PedidoDto findById(Long id) {
        return MAPPER.toDto(this.repository.findById(id).orElse(null));
    }

    @Override
    public List<PedidoDto> findByClienteId(long clienteId) {
        return MAPPER.map(repository.findByClienteId(clienteId));
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status, new Date());
    }

    @Override
    public List<PedidoDto> findAllOrdered() {
        return repository.findAllOrdered()
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public List<PedidoDto> findByStatusOrderByUpdatedAtDesc(PedidoStatus status) {
        return repository.findByStatusOrderByUpdatedAtDesc(status)
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }
}