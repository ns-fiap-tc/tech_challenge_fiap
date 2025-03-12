package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.device.rest.PagamentoApi;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoController;
import br.com.fiap.lanchonete.business.adapter.controller.PedidoController;
import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pagamento-service/v1")
@Tag(name = "pagamento-service")
public class PagamentoApiImpl implements PagamentoApi {
    private final PedidoController pedidoController;
    private final PagamentoController pagamentoController;

    @Override
    @Operation(summary = "Metodo que atualiza o status do pagamento (webhook). Metodo definido como POST por nao ser idempotente.", method = "POST")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do pagamento atualizado.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "ID do pagamento invalido"),
            @ApiResponse(responseCode = "404", description = "Pagamento nao encontrado")
    })
    @PostMapping("/updateStatus/{pedidoId}/{statusCode}")
    public ResponseEntity<Void> updateStatusWebhook(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "statusCode") String statusCode)
    {
        if ("100".equalsIgnoreCase(statusCode)) {
            pedidoController.updatePagamentoStatus(pedidoId, PagamentoStatus.CONFIRMADO);
        } else {
            pedidoController.updatePagamentoStatus(pedidoId, PagamentoStatus.RECUSADO);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Operation(summary = "Metodo que busca o pagamento de um determinado pedido.", method = "GET")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do pagamento.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "ID do pedido invalido"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado")
    })
    @GetMapping("/findByPedidoId/{pedidoId}")
    public ResponseEntity<PagamentoDto> findByPedidoId(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId) {
        PagamentoDto dto = pagamentoController.findByPedidoId(pedidoId);
        return ResponseEntity.ok(dto);
    }
}