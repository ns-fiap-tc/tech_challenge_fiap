package br.com.fiap.pagamentomock.adapter.input.controller;

import br.com.fiap.pagamentomock.adapter.output.rest.client.PagamentoWebhookClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pagamento-mock-service/v1")
@Tag(name = "pagamento-mock-service")
public class PagamentoMockApiImpl implements PagamentoMockApi {

    private final PagamentoWebhookClient pagamentoWebhookClient;

    @Override
    @Operation(summary = "Recebe a requisicao que seria o equivalente ao recebimento pelo Mercado Pago, por isso ele está como POST, por não ser idempotente.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content) })
    @PostMapping("/realizarPagamento/{pedidoId}/{valor}")
    public ResponseEntity<Boolean> realizarPagamento(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "valor") BigDecimal valor)
    {
        log.info("Entrei no metodo para chamar o sistema de pagamento: " + pedidoId + "/" + valor);
        return ResponseEntity.ok(true);
    }

    @Override
    @Operation(summary = "Executa o webhook de confirmacao de pagamento, simulando a execucao do processo de pagamento no Mercado Pago.  Este metodo foi definido como POST por nao ser idenpotente.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = @Content) })
    @PostMapping("/callPagamentoWebHook/{pedidoId}/{aprovarPagamento}")
    public ResponseEntity<String> retornarConfirmacaoPagamentoWebHookCall(
            @NotNull @PathVariable(value = "pedidoId") Long pedidoId,
            @NotNull @PathVariable(value = "aprovarPagamento") Boolean aprovarPagamento)
    {
        String statusCode = Boolean.TRUE.equals(aprovarPagamento) ? "100" : "999";
        String response = pedidoId.toString() + ";" + statusCode;
        //call webhook
        pagamentoWebhookClient.updateStatus(pedidoId, statusCode);
        log.info("Entrei no metodo para chamar o webhook: " + pedidoId + "/" + aprovarPagamento);
        return ResponseEntity.ok(response);
    }
}