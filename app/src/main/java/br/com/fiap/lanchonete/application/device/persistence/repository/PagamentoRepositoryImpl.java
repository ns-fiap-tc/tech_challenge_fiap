package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.common.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PagamentoRepositoryImpl implements PagamentoRepository {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoJpaRepository repository;

    @Override
    public PagamentoDto save(PagamentoDto dto) {
        return MAPPER.toDto(
                repository.save(
                        MAPPER.toEntity(dto)));
    }

    @Override
    public PagamentoDto findById(Long id) {
        return MAPPER.toDto(this.repository.findById(id).orElse(null));
    }

    @Override
    public List<PagamentoDto> findAll() {
        return this.repository.findAll()
                .stream()
                .map(MAPPER::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, PagamentoStatus status) {
        this.repository.updateStatus(id, status, new Date());
    }

    @Override
    public PagamentoDto findByPedidoId(Long pedidoId) {
        return MAPPER.toDto(this.repository.findByPedidoId(pedidoId));
    }
}