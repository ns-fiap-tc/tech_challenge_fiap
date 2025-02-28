package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.business.adapter.presenter.ProdutoPresenter;
import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import br.com.fiap.lanchonete.business.common.persistence.ProdutoRepository;
import br.com.fiap.lanchonete.business.core.usecase.ProdutoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.ProdutoUseCasesImpl;
import java.util.List;

public class ProdutoController {
    private final ProdutoUseCases useCase;
    private final ProdutoPresenter presenter;

    public ProdutoController(ProdutoRepository produtoRepository) {
        useCase = new ProdutoUseCasesImpl(new ProdutoGateway(produtoRepository));
        presenter = new ProdutoPresenter();
    }

    ProdutoUseCases getUseCases() {
        return this.useCase;
    }

    public ProdutoDto save(ProdutoDto produtoDto) {
        return presenter.toDto(useCase.save(presenter.toDomain(produtoDto)));
    }

    public ProdutoDto findById(Long id) {
        return presenter.toDto(useCase.findById(id));
    }

    public List<ProdutoDto> findByNome(String nome) {
        return presenter.map(useCase.findByNome(nome));
    }

    public List<ProdutoDto> findAll() {
        return presenter.map(useCase.findAll());
    }

    public List<ProdutoDto> findByCategoria(Long categoriaId) {
        return presenter.map(useCase.findByCategoria(categoriaId));
    }

    public void deleteById(Long id) {
        useCase.deleteById(id);
    }
}