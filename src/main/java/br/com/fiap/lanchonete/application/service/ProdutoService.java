package br.com.fiap.lanchonete.application.service;

import br.com.fiap.lanchonete.domain.model.Produto;
import br.com.fiap.lanchonete.domain.port.output.persistence.ProdutoRepository;
import br.com.fiap.lanchonete.domain.usecase.ProdutoUseCases;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoService implements ProdutoUseCases {

    private final ProdutoRepository repository;

    @Override
    public Produto save(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public Produto findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Produto> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public List<Produto> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Produto> findByCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
