package br.com.fiap.lanchonete.produto.core.usecase.impl;

import br.com.fiap.lanchonete.produto.adapter.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.produto.core.domain.Produto;
import br.com.fiap.lanchonete.produto.core.usecase.ProdutoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProdutoUseCasesImpl implements ProdutoUseCases {
    private final ProdutoGateway gateway;

    @Override
    public Produto save(Produto produto) {
        return gateway.save(produto);
    }

    @Override
    public Produto findById(Long id) {
        return gateway.findById(id);
    }

    @Override
    public List<Produto> findByNome(String nome) {
        return gateway.findByNome(nome);
    }

    @Override
    public List<Produto> findAll() {
        return gateway.findAll();
    }

    @Override
    public List<Produto> findByCategoria(Long categoriaId) {
        return gateway.findByCategoriaId(categoriaId);
    }

    @Override
    public void deleteById(Long id) {
        gateway.deleteById(id);
    }
}