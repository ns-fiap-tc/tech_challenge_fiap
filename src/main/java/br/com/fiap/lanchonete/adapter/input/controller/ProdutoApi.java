package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ProdutoDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ProdutoApi {
    ResponseEntity<Long> create(ProdutoDto produtoDto);
    ResponseEntity<ProdutoDto> update(long id, ProdutoDto produtoDto);
    ResponseEntity<ProdutoDto> findById(Long id);
    ResponseEntity<List<ProdutoDto>> findByNome(String nome);
    ResponseEntity<List<ProdutoDto>> findAll();
    ResponseEntity<List<ProdutoDto>> findByCategoria(Long categoriaId);
    ResponseEntity<Void> deleteById(Long id);
}
