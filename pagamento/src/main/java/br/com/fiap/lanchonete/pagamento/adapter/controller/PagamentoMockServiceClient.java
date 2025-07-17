package br.com.fiap.lanchonete.pagamento.adapter.controller;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;

public interface PagamentoMockServiceClient {
    Boolean realizarPagamento(
            @NotNull @PathVariable(value = "id") Long id,
            @NotNull @PathVariable(value = "valor") BigDecimal valor);
}