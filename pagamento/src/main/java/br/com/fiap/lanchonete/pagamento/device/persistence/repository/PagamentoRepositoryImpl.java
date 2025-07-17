package br.com.fiap.lanchonete.pagamento.device.persistence.repository;

import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.commons.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.pagamento.commons.persistence.PagamentoRepository;
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
        Date now = new Date();
        if (dto.getId() == null) {
            dto.setCreatedAt(now);
        }
        dto.setUpdatedAt(now);
        return MAPPER.toDto(
                repository.save(
                        MAPPER.toEntity(dto)));
    }

    @Override
    public PagamentoDto findById(String id) {
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
    public void deleteById(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public void updateStatus(Long pedidoId, PagamentoStatus status) {
        this.repository.updateStatus(pedidoId, status, new Date());
    }

    @Override
    public PagamentoDto findByPedidoId(Long pedidoId) {
        return MAPPER.toDto(this.repository.findByPedidoId(pedidoId));
    }
}