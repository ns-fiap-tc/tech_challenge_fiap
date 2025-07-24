package br.com.fiap.lanchonete.produto.core.usecase;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.produto.core.domain.Produto;
import java.util.List;

public interface ProdutoUseCases {
    Produto save(Produto produto);
    Produto findById(Long id);
    List<Produto> findByNome(String nome);
    List<Produto> findAll();
    List<Produto> findByCategoria(CategoriaTipoEnum categoriaTipoEnum);
    void deleteById(Long id);
}