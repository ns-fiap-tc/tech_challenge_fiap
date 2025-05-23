package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.ClienteGateway;
import br.com.fiap.lanchonete.business.adapter.presenter.ClientePresenter;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import br.com.fiap.lanchonete.business.common.persistence.ClienteRepository;
import br.com.fiap.lanchonete.business.core.usecase.ClienteUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.ClienteUseCasesImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClienteController {
    private final ClienteUseCases useCase;
    private final ClientePresenter presenter;

    public ClienteController(ClienteRepository clienteRepository) {
        useCase = new ClienteUseCasesImpl(new ClienteGateway(clienteRepository));
        presenter = new ClientePresenter();
    }

    public ClienteDto create(ClienteDto clienteDto) {
        return presenter.toDto(useCase.save(presenter.toDomain(clienteDto)));
    }

    public ClienteDto update(ClienteDto clienteDto) {
        return presenter.toDto(useCase.save(presenter.toDomain(clienteDto)));
    }

    public ClienteDto findByCpf(String cpf) {
        return presenter.toDto(useCase.findByCpf(cpf));
    }

    public List<ClienteDto> findAll() {
        return presenter.mapToDto(useCase.findAll());
    }

    public ClienteDto findByEmail(String email) {
        return presenter.toDto(useCase.findByEmail(email));
    }

    public void deleteByEmail(String email) {
        useCase.deleteByEmail(email);
    }

    public void deleteByCpf(String cpf) {
        useCase.deleteByCpf(cpf);
    }
}