package br.com.fiap.pagamentomock.adapter.output.rest.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pagamento-webhook-client")
public interface PagamentoWebhookClient {

    @PostMapping("/updateStatus/{pedidoId}/{statusCode}")
    void updateStatus(
            @NotNull @PathVariable("pedidoId") Long pedidoId,
            @NotNull @PathVariable("statusCode") String statusCode);
}