package br.com.fiap.lanchonete.business.core.usecase;

import br.com.fiap.lanchonete.business.core.domain.Produto;
import java.util.List;

public interface ProdutoUseCases {
    Produto save(Produto produto);
    Produto findById(Long id);
    List<Produto> findByNome(String nome);
    List<Produto> findAll();
    List<Produto> findByCategoria(Long categoriaId);
    void deleteById(Long id);
}