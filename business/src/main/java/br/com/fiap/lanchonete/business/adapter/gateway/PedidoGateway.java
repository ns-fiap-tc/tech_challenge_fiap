package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RequiredArgsConstructor
public class PedidoGateway {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoRepository repository;

    public Pedido save(Pedido pedido) {
        log.info("Pedido na gateway: " + pedido);
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
        Pedido persistedPedido = this.findById(pedido.getId());
        persistedPedido.setPagamentoId(pedido.getPagamentoId());
        persistedPedido.setStatus(pedido.getStatus());
        List<PedidoItem> persistedItens = persistedPedido.getItens();
        List<PedidoItem> itens = pedido.getItens();
        persistedItens.clear();
        persistedItens.addAll(itens);
        return persistedPedido;
    }

    public List<Pedido> findAllOrdered() {
        return MAPPER.mapToDomain(repository.findAllOrdered());
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