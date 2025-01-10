package br.com.fiap.lanchonete.domain.port.output.persistence;

import br.com.fiap.lanchonete.domain.model.Produto;
import java.util.List;

public interface ProdutoRepository {
    Produto save(Produto produto);
    Produto findById(Long id);
    List<Produto> findByNome(String nome);
    List<Produto> findAll();
    List<Produto> findByCategoriaId(Long categoriaId);
    void deleteById(Long id);
}
