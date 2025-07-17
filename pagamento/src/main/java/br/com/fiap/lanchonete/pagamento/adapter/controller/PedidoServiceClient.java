package br.com.fiap.lanchonete.pagamento.adapter.controller;

import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PedidoServiceClient {
    ResponseEntity<Void> updateStatus(
            @NotNull @PathVariable(value = "id") long id,
            @Valid @RequestBody PedidoStatus pedidoStatus);
}