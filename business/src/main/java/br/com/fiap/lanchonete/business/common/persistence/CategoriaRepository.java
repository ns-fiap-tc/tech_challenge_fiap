package br.com.fiap.lanchonete.business.common.persistence;

import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import java.util.List;

public interface CategoriaRepository {
    CategoriaDto save(CategoriaDto dto);
    CategoriaDto findById(Long id);
    List<CategoriaDto> findByNome(String nome);
    List<CategoriaDto> findAll();
    void deleteById(Long id);
}