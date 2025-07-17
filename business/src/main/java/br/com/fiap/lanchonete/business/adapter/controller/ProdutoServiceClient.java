package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import java.util.List;

public interface ProdutoServiceClient {

    ProdutoDto create(ProdutoDto produtoDto);

    ProdutoDto update(long id, ProdutoDto produtoDto);

    ProdutoDto findById(Long id);

    List<ProdutoDto> findByNome(String nome);

    List<ProdutoDto> findAll();

    List<ProdutoDto> findByCategoria(Long categoriaId);

    void deleteById(Long id);
}