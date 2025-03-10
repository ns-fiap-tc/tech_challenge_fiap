package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.List;

public interface PedidoUseCases {
    Pedido create(Pedido pedido);
    Pedido update(Pedido pedido);
    List<Pedido> findAllOrdered();
    List<Pedido> findByStatus(PedidoStatus status);
    Pedido findById(long id);
    List<Pedido> findByCliente(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
    void updatePagamentoStatus(Long pedidoId, PagamentoStatus status);
}