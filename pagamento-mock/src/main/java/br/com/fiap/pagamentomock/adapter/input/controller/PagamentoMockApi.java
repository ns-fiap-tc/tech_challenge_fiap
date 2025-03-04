package br.com.fiap.pagamentomock.adapter.input.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PagamentoMockApi {
    ResponseEntity<Boolean> callPagamentoWebHook(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "aprovarPagamento") Boolean aprovarPagamento);
}