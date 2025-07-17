package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import java.util.List;

public interface PedidoUseCases {
    Pedido create(PedidoDto pedidoDto);
    Pedido update(PedidoDto pedidoDto);
    List<Pedido> findAllOrdered();
    List<Pedido> findByStatus(PedidoStatus status);
    Pedido findById(long id);
    List<Pedido> findByCliente(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
    void updatePagamentoStatus(Long pedidoId, PagamentoStatus status);
}