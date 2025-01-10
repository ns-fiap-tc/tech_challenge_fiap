package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.PedidoMapper;
import br.com.fiap.lanchonete.domain.model.Pedido;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.port.output.persistence.PedidoRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoRepositoryImpl implements PedidoRepository {

    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoJpaRepository repository;

    @Override
    public Pedido save(Pedido pedido) {
        Date now = new Date();
        if (pedido.getId() == null) {
            pedido.setCreatedAt(now);
        }
        if (pedido.getClienteId() == null) {
            pedido.setClienteId(-1L);
        }
        pedido.setUpdatedAt(now);
        return MAPPER.toDomain(
                this.repository.save(MAPPER.toEntity(pedido)));
    }

    @Override
    public List<Pedido> findAll() {
        return MAPPER.map(repository.findAll());
    }

    @Override
    public Pedido findById(Long id) {
        return MAPPER.toDomain(this.repository.findById(id).orElse(null));
    }

    @Override
    public List<Pedido> findByClienteId(long clienteId) {
        return MAPPER.map(repository.findByClienteId(clienteId));
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        repository.updateStatus(id, status, new Date());
    }
}
