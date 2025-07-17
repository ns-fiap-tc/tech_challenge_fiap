package br.com.fiap.lanchonete.pagamento.device.rest.impl;

import br.com.fiap.lanchonete.pagamento.adapter.controller.PagamentoController;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.device.rest.PagamentoApi;
import br.com.fiap.lanchonete.pagamento.device.rest.client.PedidoServiceClientImpl;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pagamento-service/v1")
@Tag(name = "pagamento-service")
public class PagamentoApiImpl implements PagamentoApi {
    private final PagamentoController controller;
    private final PedidoServiceClientImpl pedidoServiceClient;

    @Override
    @Operation(summary = "Criar um novo pagamento. Retorna o id do objeto criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<PagamentoDto> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody PagamentoDto pagamentoDto) {
        PagamentoDto dtoNew = controller.save(pagamentoDto);
        return ResponseEntity.ok(dtoNew);
    }
/*
    @Override
    @Operation(summary = "Atualizar um pagamento existente. Retorna o objeto atualizado.", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @PutMapping("/save/{id}")
    public ResponseEntity<PagamentoDto> update(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser atualizado")
            @Valid @RequestBody PagamentoDto pagamentoDto)
    {
        pagamentoDto.setId(id);
        return ResponseEntity.ok(controller.save(pagamentoDto));
    }
*/

    @Override
    @Operation(summary = "Criar um novo pagamento. Retorna o id do objeto criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/realizarPagamento")
    public ResponseEntity<PagamentoDto> realizarPagamento(@Valid @RequestBody PagamentoDto pagamentoDto) {
        return ResponseEntity.ok(controller.realizarPagamento(pagamentoDto));
    }

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
            controller.updateStatus(pedidoId, PagamentoStatus.CONFIRMADO);
            pedidoServiceClient.updateStatus(pedidoId, PedidoStatus.PREPARACAO);
        } else {
            controller.updateStatus(pedidoId, PagamentoStatus.RECUSADO);
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
        return ResponseEntity.ok(controller.findByPedidoId(pedidoId));
    }

    @Override
    @Operation(summary = "Busca os pagamentos a partir do ID.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<PagamentoDto> findById(
            @NotNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(controller.findById(id));
    }
}