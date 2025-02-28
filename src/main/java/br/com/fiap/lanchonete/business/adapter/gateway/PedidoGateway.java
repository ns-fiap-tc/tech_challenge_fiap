package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PedidoGateway {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoRepository repository;

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
        return MAPPER.toDomain(repository.save(MAPPER.toDto(pedido)));
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

    public List<Pedido> findAll() {
        return MAPPER.mapToDomain(repository.findByOrderByIdDesc());
    }

    public List<Pedido> findByStatus(PedidoStatus status) {
        return MAPPER.mapToDomain(repository.findByStatusOrderByUpdatedAtDesc(status));
    }

    public Pedido findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<Pedido> findByClienteId(long clienteId) {
        return MAPPER.mapToDomain(repository.findByClienteId(clienteId));
    }

    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status);
    }
}