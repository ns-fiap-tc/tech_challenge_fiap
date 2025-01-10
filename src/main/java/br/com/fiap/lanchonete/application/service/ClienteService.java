package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.port.output.persistence.ClienteRepository;
import br.com.fiap.lanchonete.domain.usecase.ClienteUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService implements ClienteUseCases {

    private final ClienteRepository repository;

    @Override
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Cliente findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteByCpf(String cpf) {
        repository.deleteByCpf(cpf);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}
