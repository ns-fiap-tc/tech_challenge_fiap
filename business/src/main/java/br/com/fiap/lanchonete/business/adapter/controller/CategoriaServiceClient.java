package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import java.util.List;

public interface CategoriaServiceClient {

    CategoriaDto create(CategoriaDto categoriaDto);

    CategoriaDto update(long id, CategoriaDto categoriaDto);

    void deleteById(Long id);

    List<CategoriaDto> findAll();

    CategoriaDto findById(Long id);

    List<CategoriaDto> findByNome(String nome);

    CategoriaDto findByCategoria(CategoriaTipoEnum categoriaTipoEnum);
}