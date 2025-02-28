package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.CategoriaGateway;
import br.com.fiap.lanchonete.business.adapter.presenter.CategoriaPresenter;
import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import br.com.fiap.lanchonete.business.common.persistence.CategoriaRepository;
import br.com.fiap.lanchonete.business.core.usecase.CategoriaUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.CategoriaUseCasesImpl;
import java.util.List;

public class CategoriaController {
    private final CategoriaUseCases useCase;
    private final CategoriaPresenter presenter;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        useCase = new CategoriaUseCasesImpl(new CategoriaGateway(categoriaRepository));
        presenter = new CategoriaPresenter();
    }

    CategoriaUseCases getUseCases() {
        return this.useCase;
    }

    public CategoriaDto create(CategoriaDto categoriaDto) {
        return presenter.toDto(useCase.save(presenter.toDomain(categoriaDto)));
    }

    public void deleteById(Long id) {
        useCase.deleteById(id);
    }

    public List<CategoriaDto> findAll() {
        return presenter.map(useCase.findAll());
    }

    public CategoriaDto findById(Long id) {
        return presenter.toDto(useCase.findById(id));
    }

    public List<CategoriaDto> findByNome(String nome) {
        return presenter.map(useCase.findByNome(nome));
    }

    public CategoriaDto update(CategoriaDto categoriaDto) {
        return presenter.toDto(useCase.save(presenter.toDomain(categoriaDto)));
    }
}