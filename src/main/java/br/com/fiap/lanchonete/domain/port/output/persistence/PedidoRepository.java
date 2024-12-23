package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;

public interface PedidoRepository {
    Pedido save(Pedido pedido);
    Pedido findById(Long id);
    Pedido findByClienteCpf(String clienteCpf);
    void updateStatus(PedidoStatus status);
}
