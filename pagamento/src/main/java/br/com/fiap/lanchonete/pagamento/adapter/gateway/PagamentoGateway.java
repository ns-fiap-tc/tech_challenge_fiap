package br.com.fiap.lanchonete.pagamento.adapter.gateway;

import br.com.fiap.lanchonete.pagamento.commons.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.pagamento.core.domain.Pagamento;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PagamentoGateway {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoRepository repository;

    public Pagamento save(Pagamento pagamento) {
        return MAPPER.toDomain(repository.save(MAPPER.toDto(pagamento)));
    }

    public Pagamento findById(String id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<Pagamento> findAll() {
        return MAPPER.mapDtoToDomain(repository.findAll());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void updateStatus(Long pedidoId, PagamentoStatus status) {
        repository.updateStatus(pedidoId, status);
    }

    public Pagamento findByPedidoId(Long pedidoId) {
        return MAPPER.toDomain(repository.findByPedidoId(pedidoId));
    }
}