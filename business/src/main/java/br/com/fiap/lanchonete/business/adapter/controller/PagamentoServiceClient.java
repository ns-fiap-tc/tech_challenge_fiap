package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;

public interface PagamentoServiceClient {
//    PagamentoDto create(PagamentoDto pagamentoDto);

 //   PagamentoDto update(long id, PagamentoDto pagamentoDto);

    PagamentoDto save(PagamentoDto pagamentoDto);

    PagamentoDto realizarPagamento(PagamentoDto pagamentoDto);

    PagamentoDto findByPedidoId(Long pedidoId);

    PagamentoDto findById(Long id);
}