package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import java.util.List;

public interface PedidoRepository {
    Pedido save(Pedido pedido);
    List<Pedido> findAll();
    List<Pedido> findByStatus(PedidoStatus status);
    Pedido findById(Long id);
    List<Pedido> findByClienteId(long clienteId);
    void updateStatus(Long id, PedidoStatus status);
}
