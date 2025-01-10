package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.CategoriaDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CategoriaApi {
    ResponseEntity<Long> create(CategoriaDto categoriaDto);
    ResponseEntity<CategoriaDto> update(long id, CategoriaDto categoriaDto);
    ResponseEntity<CategoriaDto> findById(Long id);
    ResponseEntity<List<CategoriaDto>> findAll();
    ResponseEntity<List<CategoriaDto>> findByNome(String nome);
    ResponseEntity<Void> deleteById(Long id);
}
