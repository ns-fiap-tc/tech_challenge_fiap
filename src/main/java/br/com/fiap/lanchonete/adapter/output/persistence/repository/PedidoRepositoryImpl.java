package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.PedidoMapper;
import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoRepositoryImpl implements PedidoRepository {

    private final PedidoJpaRepository repository;
    private final PedidoMapper mapper = PedidoMapper.INSTANCE;

    @Override
    public Pedido save(Pedido pedido) {
        return this.mapper.toDomain(
                this.repository.save(this.mapper.toJpaEntity(pedido)));
    }

    @Override
    public Pedido findById(Long id) {
        return this.mapper.toDomain(this.repository.findById(id).orElse(null));
    }

    @Override
    public Pedido findByClienteCpf(String clienteCpf) {
        return null;
    }

    @Override
    public void updateStatus(PedidoStatus status) {

    }
}
