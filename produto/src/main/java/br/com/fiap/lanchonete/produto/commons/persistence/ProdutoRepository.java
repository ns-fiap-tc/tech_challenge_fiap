package br.com.fiap.lanchonete.produto.commons.persistence;

import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import java.util.List;

public interface ProdutoRepository {
    ProdutoDto save(ProdutoDto produtoDto);
    ProdutoDto findById(Long id);
    List<ProdutoDto> findByNome(String nome);
    List<ProdutoDto> findAll();
    List<ProdutoDto> findByCategoriaId(Long categoriaId);
    void deleteById(Long id);
}