package br.com.fiap.lanchonete.business.adapter.controller;

import java.math.BigDecimal;

public interface PagamentoServiceClient {
    Boolean realizarPagamento(Long id, BigDecimal valor);
}