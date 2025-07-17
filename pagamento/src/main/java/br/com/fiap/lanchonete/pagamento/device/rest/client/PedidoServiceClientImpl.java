package br.com.fiap.lanchonete.pagamento.device.rest.client;

import br.com.fiap.lanchonete.pagamento.adapter.controller.PedidoServiceClient;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pedido-service-client")
public interface PedidoServiceClientImpl extends PedidoServiceClient {

    @PutMapping("/updateStatus/{id}")
    ResponseEntity<Void> updateStatus(
            @NotNull @PathVariable(value = "id") long id,
            @Valid @RequestBody PedidoStatus pedidoStatus);
}