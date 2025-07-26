package br.com.fiap.lanchonete.pagamento.device.rest;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PagamentoApi {

    ResponseEntity<PagamentoDto> save(@Valid @RequestBody PagamentoDto pagamentoDto);

    ResponseEntity<PagamentoDto> realizarPagamento(@Valid @RequestBody PagamentoDto pagamentoDto);

    ResponseEntity<Void> updateStatusWebhook(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "statusCode") String statusCode);

    ResponseEntity<PagamentoDto> findByPedidoId(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId);

    ResponseEntity<PagamentoDto> findById(
            @NotNull @PathVariable(value = "id") String id);
}