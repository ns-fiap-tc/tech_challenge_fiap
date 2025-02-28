package br.com.fiap.lanchonete.application.adapter.rest;

import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CategoriaApi {
    ResponseEntity<Long> create(@Valid @RequestBody CategoriaDto categoriaDto);
    ResponseEntity<CategoriaDto> update(@NotNull @PathVariable(value = "id") long id, @Valid @RequestBody CategoriaDto categoriaDto);
    ResponseEntity<CategoriaDto> findById(@NotNull @PathVariable(value = "id") Long id);
    ResponseEntity<List<CategoriaDto>> findAll();
    ResponseEntity<List<CategoriaDto>> findByNome(@NotNull @PathVariable(value = "nome") String nome);
    ResponseEntity<Void> deleteById(@NotNull @PathVariable(value = "id") Long id);
}