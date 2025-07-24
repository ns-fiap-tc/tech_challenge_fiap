package br.com.fiap.lanchonete.produto.commons.persistence;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import java.util.List;

public interface ProdutoRepository {
    ProdutoDto save(ProdutoDto produtoDto);
    ProdutoDto findById(Long id);
    List<ProdutoDto> findByNome(String nome);
    List<ProdutoDto> findAll();
    List<ProdutoDto> findByCategoriaTipoEnum(CategoriaTipoEnum categoriaTipoEnum);
    void deleteById(Long id);
}