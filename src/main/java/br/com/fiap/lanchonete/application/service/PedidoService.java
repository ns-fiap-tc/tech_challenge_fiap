package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import br.com.fiap.lanchonete.domain.usecase.PedidoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoService implements PedidoUseCases {

    private final PedidoRepository repository;

    @Override
    public Pedido save(Pedido pedido) {
        //gravacao inicial do pedido para posteriores atualziacoes.
        if (pedido.getId() == null) {
            pedido = repository.save(pedido);
        }

        //direcionar para o pagamento.
        //atualizar status no bean e na base de dados conforme retorno do pagamento
        //encaminhar os itens que requerem preparo para a cozinha.
        //aguardar retorno da cozinha com status de finalizado para cada item.
        return repository.save(pedido);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @Override
    public Pedido findById(long id) {
        return null;
    }

    @Override
    public List<Pedido> findByCliente(long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status);
    }
}
