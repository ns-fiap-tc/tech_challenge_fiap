package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ProdutoDto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProdutoApi {
    ResponseEntity<Long> create(@Valid @RequestBody ProdutoDto produtoDto);
    ResponseEntity<ProdutoDto> update(@NotNull @PathVariable(value = "id") long id, @Valid @RequestBody ProdutoDto produtoDto);
    ResponseEntity<ProdutoDto> findById(@NotNull @PathVariable(value = "id") Long id);
    ResponseEntity<List<ProdutoDto>> findByNome(@NotNull @PathVariable(value = "nome") String nome);
    ResponseEntity<List<ProdutoDto>> findAll();
    ResponseEntity<List<ProdutoDto>> findByCategoria(@NotNull @PathVariable(value = "categoriaId") Long categoriaId);
    ResponseEntity<Void> deleteById(@NotNull @PathVariable(value = "id") Long id);
}
