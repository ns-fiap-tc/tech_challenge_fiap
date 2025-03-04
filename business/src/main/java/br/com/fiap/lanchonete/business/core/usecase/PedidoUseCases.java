package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.List;

public interface PedidoUseCases {
    Pedido create(Pedido pedido);
    Pedido update(Pedido pedido);
    List<Pedido> findAll();
    List<Pedido> findByStatus(PedidoStatus status);
    Pedido findById(long id);
    List<Pedido> findByCliente(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
    void validarPedidoStatus(Long id);
    void retryPayment(long pedidoId,boolean statusPagamento);
}