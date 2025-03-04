package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.Date;
import java.util.List;
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

        persistedPedido.setStatus(pedido.getStatus());

        List<PedidoItem> persistedItens = persistedPedido.getItens();
        List<PedidoItem> itens = pedido.getItens();

        persistedItens.clear();
        persistedItens.addAll(itens);
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