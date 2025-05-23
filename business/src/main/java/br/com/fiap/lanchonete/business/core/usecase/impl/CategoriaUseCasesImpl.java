package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.CategoriaGateway;
import br.com.fiap.lanchonete.business.core.domain.Categoria;
import br.com.fiap.lanchonete.business.core.usecase.CategoriaUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaUseCasesImpl implements CategoriaUseCases {
    private final CategoriaGateway gateway;

    @Override
    public Categoria save(Categoria categoria) {
        return gateway.save(categoria);
    }

    @Override
    public Categoria findById(Long id) {
        return gateway.findById(id);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return gateway.findByNome(nome);
    }

    @Override
    public List<Categoria> findAll() {
        return gateway.findAll();
    }

    @Override
    public void deleteById(Long id) {
        gateway.deleteById(id);
    }
}