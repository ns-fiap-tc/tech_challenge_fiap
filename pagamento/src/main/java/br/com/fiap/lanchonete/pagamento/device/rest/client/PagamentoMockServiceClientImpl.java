package br.com.fiap.lanchonete.pagamento.device.rest.client;

import br.com.fiap.lanchonete.pagamento.adapter.controller.PagamentoMockServiceClient;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "pagamentoMock-service-client")
public interface PagamentoMockServiceClientImpl extends PagamentoMockServiceClient {

    @PostMapping("/realizarPagamento/{id}/{valor}")
    Boolean realizarPagamento(
            @NotNull @PathVariable(value = "id") Long id,
            @NotNull @PathVariable(value = "valor") BigDecimal valor);
}