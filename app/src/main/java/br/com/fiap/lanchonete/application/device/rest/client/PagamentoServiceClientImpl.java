package br.com.fiap.lanchonete.application.device.rest.client;

import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamento-service-client")
public interface PagamentoServiceClientImpl extends PagamentoServiceClient {

    @PostMapping("/save")
    PagamentoDto save(@NotNull PagamentoDto pagamentoDto);

    @PostMapping("/realizarPagamento")
    PagamentoDto realizarPagamento(@Valid @RequestBody PagamentoDto pagamentoDto);

    @GetMapping("/findById/{id}")
    PagamentoDto findById(@NotNull @PathVariable(value = "id") Long id);

    @GetMapping("/findByPedidoId/{pedidoId}")
    PagamentoDto findByPedidoId(@NotNull @PathVariable(value = "pedidoId") Long pedidoId);
}