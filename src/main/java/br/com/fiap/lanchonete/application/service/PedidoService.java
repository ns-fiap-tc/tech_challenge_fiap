package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import br.com.fiap.lanchonete.domain.usecase.PedidoUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoService implements PedidoUseCases {

    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Pedido findByClienteCpf(String clienteCpf) {
        return pedidoRepository.findByClienteCpf(clienteCpf);
    }

    @Override
    public void updateStatus(PedidoStatus status) {
        pedidoRepository.updateStatus(status);
    }
}
