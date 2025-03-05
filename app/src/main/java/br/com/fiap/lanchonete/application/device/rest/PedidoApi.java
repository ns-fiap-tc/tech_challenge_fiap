package br.com.fiap.lanchonete.application.device.rest;

import br.com.fiap.lanchonete.business.common.dto.PedidoDto;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PedidoApi {
    ResponseEntity<PedidoDto> create(@Valid @RequestBody PedidoDto pedidoDto);
    ResponseEntity<PedidoDto> update(@NotNull @PathVariable(value = "id") long id, @Valid @RequestBody PedidoDto pedidoDto);
    ResponseEntity<Void> updateStatus(@NotNull @PathVariable(value = "id") long id, @Valid @RequestBody PedidoStatus pedidoStatus);
    ResponseEntity<List<PedidoDto>> findAll();
    ResponseEntity<List<PedidoDto>> findByStatus(@NotNull @PathVariable(value = "status") PedidoStatus status);
    ResponseEntity<PedidoDto> findById(@NotNull @PathVariable(value = "id") long id);
    ResponseEntity<List<PedidoDto>> findByCliente(@NotNull @PathVariable(value = "id") long clienteId);
    ResponseEntity<Void> retryPayment(@NotNull @PathVariable(value = "id") long id, @NotNull @PathVariable(value = "paymentStatus") boolean paymentStatus);
}