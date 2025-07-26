package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;

public interface PagamentoServiceClient {

    PagamentoDto save(PagamentoDto pagamentoDto);

    PagamentoDto realizarPagamento(PagamentoDto pagamentoDto);

    PagamentoDto findByPedidoId(Long pedidoId);

    PagamentoDto findById(Long id);
}