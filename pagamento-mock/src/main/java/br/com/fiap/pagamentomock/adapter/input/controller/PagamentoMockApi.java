package br.com.fiap.pagamentomock.adapter.input.controller;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PagamentoMockApi {

    ResponseEntity<Boolean> realizarPagamento(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "valor") BigDecimal valor);

    ResponseEntity<String> retornarConfirmacaoPagamentoWebHookCall(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "aprovarPagamento") Boolean aprovarPagamento);
}