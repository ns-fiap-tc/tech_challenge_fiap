package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.ProdutoDto;
import java.util.List;

public interface ProdutoRepository {
    ProdutoDto save(ProdutoDto produto);
    ProdutoDto findById(Long id);
    List<ProdutoDto> findByNome(String nome);
    List<ProdutoDto> findAll();
    List<ProdutoDto> findByCategoriaId(Long categoriaId);
    void deleteById(Long id);
}