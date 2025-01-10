package br.com.fiap.lanchonete.domain.usecase;

import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import java.util.List;

public interface PedidoUseCases {
    Pedido save(Pedido pedido);
    List<Pedido> findAll();
    Pedido findById(long id);
    List<Pedido> findByCliente(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
}
