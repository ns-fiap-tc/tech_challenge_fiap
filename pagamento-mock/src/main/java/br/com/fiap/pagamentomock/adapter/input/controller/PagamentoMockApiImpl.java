package br.com.fiap.pagamentomock.adapter.input.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pagamento-mock-service/v1")
@Tag(name = "pagamento-mock-service")
public class PagamentoMockApiImpl implements PagamentoMockApi {

    @Override
    @Operation(summary = "Executa o webhook de confirmacao de pagamento, simulando a execucao do processo de pagamento no Mercado Pago.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content) })
    @GetMapping("/callPagamentoWebHook/{pedidoId}/{aprovarPagamento}")
    public ResponseEntity<Boolean> callPagamentoWebHook(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "aprovarPagamento") Boolean aprovarPagamento)
    {
        //call webhook
        log.info("Entrei no metodo para chamar o webhook: " + pedidoId + "/" + aprovarPagamento);
        return ResponseEntity.ok(true);
    }
}