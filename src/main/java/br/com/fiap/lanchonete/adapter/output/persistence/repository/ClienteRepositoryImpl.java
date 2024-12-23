package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.ClienteMapper;
import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.port.output.persistence.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository repository;
    private final ClienteMapper mapper = ClienteMapper.INSTANCE;

    @Override
    public Cliente save(Cliente cliente) {
        return this.mapper.toDomain(
                this.repository.save(
                        this.mapper.toJpaEntity(cliente)));
    }

    @Override
    public Cliente findByEmail(String email) {
        return this.mapper.toDomain(
                this.repository.findByEmail(email).orElse(null));
    }
}
