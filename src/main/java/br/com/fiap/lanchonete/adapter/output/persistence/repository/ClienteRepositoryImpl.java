package br.com.fiap.lanchonete.adapter.output.persistence.repository;

import br.com.fiap.lanchonete.adapter.output.persistence.mapper.ClienteMapper;
import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.port.output.persistence.ClienteRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClienteRepositoryImpl implements ClienteRepository {

    private static final ClienteMapper MAPPER = ClienteMapper.INSTANCE;
    private final ClienteJpaRepository repository;

    @Override
    public Cliente save(Cliente cliente) {
        Date now = new Date();
        if (cliente.getId() == null) {
            cliente.setCreatedAt(now);
        }
        cliente.setUpdatedAt(now);
        return MAPPER.toDomain(
                this.repository.save(
                        MAPPER.toEntity(cliente)));
    }

    @Override
    public Cliente findByEmail(String email) {
        return MAPPER.toDomain(
                this.repository.findByEmail(email).orElse(null));
    }

    @Override
    public Cliente findByCpf(String cpf) {
        return MAPPER.toDomain(
                this.repository.findByCpf(cpf).orElse(null));
    }

    @Override
    public List<Cliente> findAll() {
        return MAPPER.map(repository.findAll());
    }

    @Override
    public void deleteByCpf(String cpf) {
        this.repository.deleteByCpf(cpf);
    }

    @Override
    public void deleteByEmail(String email) {
        this.repository.deleteByEmail(email);
    }
}
