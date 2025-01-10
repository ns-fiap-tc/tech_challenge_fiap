package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ClienteDto;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ClienteApi {
    ResponseEntity<Long> create(ClienteDto clienteDto);
    ResponseEntity<ClienteDto> update(long id, ClienteDto clienteDto);
    ResponseEntity<ClienteDto> findByCpf(String cpf);
    ResponseEntity<List<ClienteDto>> findAll();
    ResponseEntity<ClienteDto> findByEmail(String email);
    ResponseEntity<Void> deleteByCpf(String cpf);
    ResponseEntity<Void> deleteByEmail(String email);
}
