package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PagamentoGateway {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoRepository repository;

    public Pagamento save(Pagamento pagamento) {
        Date now = new Date();
        if (pagamento.getId() == null) {
            pagamento.setCreatedAt(now);
        }
        pagamento.setUpdatedAt(now);
        return MAPPER.toDomain(repository.save(MAPPER.toDto(pagamento)));
    }

    public Pagamento findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<Pagamento> findAll() {
        return MAPPER.map(repository.findAll());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void updateStatus(Long id, PagamentoStatus status) {
        repository.updateStatus(id, status);
    }

    public Pagamento findByPedidoId(Long pedidoId) {
        return MAPPER.toDomain(repository.findByPedidoId(pedidoId));
    }
}