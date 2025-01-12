package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ClienteDto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClienteApi {
    ResponseEntity<Long> create(@Valid @RequestBody ClienteDto clienteDto);
    ResponseEntity<ClienteDto> update(@NotNull @PathVariable(value = "id") long id, @Valid @RequestBody ClienteDto clienteDto);
    ResponseEntity<ClienteDto> findByCpf(@NotNull @PathVariable(value = "cpf") String cpf);
    ResponseEntity<List<ClienteDto>> findAll();
    ResponseEntity<ClienteDto> findByEmail(@NotNull @PathVariable(value = "email") String email);
    ResponseEntity<Void> deleteByCpf(@NotNull @PathVariable(value = "cpf") String cpf);
    ResponseEntity<Void> deleteByEmail(@NotNull @PathVariable(value = "email") String email);
}
