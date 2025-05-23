package br.com.fiap.lanchonete.business.adapter.gateway;

import br.com.fiap.lanchonete.business.common.mapper.ClienteMapper;
import br.com.fiap.lanchonete.business.common.persistence.ClienteRepository;
import br.com.fiap.lanchonete.business.core.domain.Cliente;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClienteGateway {
    private static final ClienteMapper MAPPER = ClienteMapper.INSTANCE;
    private final ClienteRepository repository;

    public Cliente save(Cliente cliente) {
        Date now = new Date();
        if (cliente.getId() == null) {
            cliente.setCreatedAt(now);
        }
        cliente.setUpdatedAt(now);
        return MAPPER.toDomain(repository.save(MAPPER.toDto(cliente)));
    }

    public Cliente findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public Cliente findByEmail(String email) {
        return MAPPER.toDomain(repository.findByEmail(email));
    }

    public Cliente findByCpf(String cpf) {
        return MAPPER.toDomain(repository.findByCpf(cpf));
    }

    public List<Cliente> findAll() {
        return MAPPER.mapToDomain(repository.findAll());
    }

    public void deleteByCpf(String cpf) {
        repository.deleteByCpf(cpf);
    }

    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}