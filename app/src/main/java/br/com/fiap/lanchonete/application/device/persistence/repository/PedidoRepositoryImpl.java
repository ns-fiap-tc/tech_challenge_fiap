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
    //@Transactional(dontRollbackOn = PagamentoConfirmacaoException.class)
    public PedidoDto save(PedidoDto dto) {
/*
        if (dto.getId() == null) {
            dto.setCreatedAt(now);
        } else {
            dto = this.updatePedido(dto);
        }
        if (dto.getClienteId() == null) {
            dto.setClienteId(-1L);
        }
        dto.setUpdatedAt(now);
*/
        return MAPPER.toDto(
                this.repository.save(MAPPER.toEntity(dto)));
    }
/*
    private PedidoDto updatePedido(PedidoDto dto) {
        Date now = new Date();
        PedidoDto persistedPedido = this.findById(dto.getId());
        Pagamento persistedPagamento = persistedPedido.getPagamento();
        Pagamento transientPagamento = dto.getPagamento();
        persistedPagamento.setForma(transientPagamento.getForma());
        persistedPagamento.setStatus(transientPagamento.getStatus());
        persistedPagamento.setUpdatedAt(now);

        List<PedidoItemDto> persistedItens = persistedPedido.getItens();
        List<PedidoItemDto> itens = dto.getItens();
        if ((itens == null || itens.isEmpty()) && persistedItens != null) {
            persistedItens.clear();
        } else {
            if (persistedItens == null || persistedItens.isEmpty()) {
                persistedPedido.setItens(dto.getItens());
            } else {
                Map<Long, PedidoItemDto> itensMap = dto.getItens().stream()
                        .collect(Collectors.toMap(PedidoItemDto::getId, Function.identity()));
                for (PedidoItemDto item : itens) {
                    PedidoItemDto persistedItem = persistedItens.stream()
                            .filter(it -> item.getId().equals(it.getId()))
                            .findAny()
                            .orElse(null);

                    if (persistedItem != null) {
                        persistedItem.setQuantidade(item.getQuantidade());
                        persistedItem.setObservacoes(item.getObservacoes());
                        itensMap.remove(item.getId());
                    }
                }
                if (!itensMap.isEmpty()) {
                    persistedItens.removeAll(itensMap.values());
                }
            }
        }
        return persistedPedido;
    }
 */

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
    //@Transactional(dontRollbackOn = PagamentoConfirmacaoException.class)
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