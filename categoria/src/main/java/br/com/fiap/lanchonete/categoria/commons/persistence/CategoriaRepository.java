package br.com.fiap.lanchonete.categoria.commons.persistence;

import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import java.util.List;

public interface CategoriaRepository {
    CategoriaDto save(CategoriaDto dto);
    CategoriaDto findById(Long id);
    List<CategoriaDto> findByNome(String nome);
    List<CategoriaDto> findAll();
    void deleteById(Long id);
}