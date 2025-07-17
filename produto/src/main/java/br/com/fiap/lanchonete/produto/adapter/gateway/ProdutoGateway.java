package br.com.fiap.lanchonete.produto.adapter.gateway;

import br.com.fiap.lanchonete.produto.commons.persistence.ProdutoRepository;
import br.com.fiap.lanchonete.produto.core.domain.Produto;
import br.com.fiap.lanchonete.produto.commons.mapper.ProdutoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProdutoGateway {
    private static final ProdutoMapper MAPPER = ProdutoMapper.INSTANCE;
    private final ProdutoRepository repository;

    public Produto save(Produto produto) {
        return MAPPER.toDomain(repository.save(MAPPER.toDto(produto)));
    }

    public Produto findById(Long id) {
        return MAPPER.toDomain(repository.findById(id));
    }

    public List<Produto> findByNome(String nome) {
        return MAPPER.mapToDomain(repository.findByNome(nome));
    }

    public List<Produto> findAll() {
        return MAPPER.mapToDomain(repository.findAll());
    }

    public List<Produto> findByCategoriaId(Long categoriaId) {
        return MAPPER.mapToDomain(repository.findByCategoriaId(categoriaId));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}