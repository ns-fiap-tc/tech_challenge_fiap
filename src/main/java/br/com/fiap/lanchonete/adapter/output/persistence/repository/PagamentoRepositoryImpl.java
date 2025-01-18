package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.domain.model.Pagamento;
import br.com.fiap.lanchonete.domain.port.output.persistence.PagamentoRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoJpaRepository repository;

    @Override
    public Pagamento save(Pagamento pagamento) {
        Date now = new Date();
        if (pagamento.getId() == null) {
            pagamento.setCreatedAt(now);
        }
        pagamento.setUpdatedAt(now);
        return MAPPER.toDomain(
                repository.save(
                        MAPPER.toEntity(pagamento)));
    }

    @Override
    public Pagamento findById(Long id) {
        return MAPPER.toDomain(this.repository.findById(id).orElse(null));
    }

    @Override
    public List<Pagamento> findAll() {
        return this.repository.findAll()
                .stream()
                .map(MAPPER::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}
