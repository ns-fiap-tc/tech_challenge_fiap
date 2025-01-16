package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.PedidoDto;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface PedidoApi {
    ResponseEntity<Long> create(PedidoDto pedidoDto);
    ResponseEntity<PedidoDto> update(long id, PedidoDto pedidoDto);
    ResponseEntity<Void> updateStatus(long id, PedidoStatus pedidoStatus);
    ResponseEntity<List<PedidoDto>> findAll();
    ResponseEntity<List<PedidoDto>> findByStatus(PedidoStatus status);
    ResponseEntity<PedidoDto> findById(long id);
    ResponseEntity<List<PedidoDto>> findByCliente(long clienteId);
}
