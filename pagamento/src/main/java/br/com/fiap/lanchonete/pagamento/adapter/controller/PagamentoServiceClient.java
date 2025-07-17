package br.com.fiap.lanchonete.pagamento.adapter.controller;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;

public interface PagamentoServiceClient {
    Boolean realizarPagamento(PagamentoDto pagamentoDto);
}