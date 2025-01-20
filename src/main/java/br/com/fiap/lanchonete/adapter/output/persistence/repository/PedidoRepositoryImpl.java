package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.PedidoMapper;
import br.com.fiap.lanchonete.domain.model.Pagamento;
import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoItem;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoRepositoryImpl implements PedidoRepository {

    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoJpaRepository repository;

    @Override
    public Pedido save(Pedido pedido) {
        Date now = new Date();
        if (pedido.getId() == null) {
            pedido.setCreatedAt(now);
        } else {
            pedido = this.updatePedido(pedido);
        }
        if (pedido.getClienteId() == null) {
            pedido.setClienteId(-1L);
        }
        pedido.setUpdatedAt(now);
        return MAPPER.toDomain(
                this.repository.save(MAPPER.toEntity(pedido)));
    }

    private Pedido updatePedido(Pedido pedido) {
        Date now = new Date();
        Pedido persistedPedido = this.findById(pedido.getId());
        Pagamento persistedPagamento = persistedPedido.getPagamento();
        Pagamento transientPagamento = pedido.getPagamento();
        persistedPagamento.setForma(transientPagamento.getForma());
        persistedPagamento.setStatus(transientPagamento.getStatus());
        persistedPagamento.setUpdatedAt(now);

        List<PedidoItem> persistedItens = persistedPedido.getItens();
        List<PedidoItem> itens = pedido.getItens();
        if ((itens == null || itens.isEmpty()) && persistedItens != null) {
            persistedItens.clear();
        } else {
            if (persistedItens == null || persistedItens.isEmpty()) {
                persistedPedido.setItens(pedido.getItens());
            } else {
                Map<Long, PedidoItem> itensMap = pedido.getItens().stream()
                        .collect(Collectors.toMap(PedidoItem::getId, Function.identity()));
                for (PedidoItem item : itens) {
                    PedidoItem persistedItem = persistedItens.stream()
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

    @Override
    public List<Pedido> findAll() {
        return MAPPER.map(repository.findByOrderByIdDesc());
    }

    @Override
    public List<Pedido> findByStatus(PedidoStatus status) {
        return MAPPER.map(repository.findByStatusOrderByUpdatedAtDesc(status));
    }

    @Override
    public Pedido findById(Long id) {
        return MAPPER.toDomain(this.repository.findById(id).orElse(null));
    }

    @Override
    public List<Pedido> findByClienteId(long clienteId) {
        return MAPPER.map(repository.findByClienteId(clienteId));
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status, new Date());
    }
}