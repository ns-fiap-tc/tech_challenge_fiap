package br.com.fiap.lanchonete.application.device.rest;

import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PagamentoApi {
    ResponseEntity<Void> updateStatusWebhook(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "statusCode") String statusCode);


    ResponseEntity<PagamentoDto> findByPedidoId(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId);
}