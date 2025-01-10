package br.com.fiap.lanchonete.domain.usecase;

import br.com.fiap.lanchonete.domain.model.Produto;
import java.util.List;

public interface ProdutoUseCases {
    Produto save(Produto produto);
    Produto findById(Long id);
    List<Produto> findByNome(String nome);
    List<Produto> findAll();
    List<Produto> findByCategoria(Long categoriaId);
    void deleteById(Long id);
}
