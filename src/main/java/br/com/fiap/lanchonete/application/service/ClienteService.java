package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.adapter.output.persistence.repository.ClienteJpaRepository;
import br.com.fiap.lanchonete.domain.model.Cliente;
import br.com.fiap.lanchonete.domain.usecase.ClienteUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService implements ClienteUseCases {

    private final ClienteJpaRepository clienteJpaRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return null;
    }

    @Override
    public Cliente findByEmail(String email) {
        return null;
    }

    @Override
    public Cliente findByCpf(String cpf) {
        return null;
    }

    @Override
    public Cliente findByCelular(String celular) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByNome(String nome) {

    }
}
