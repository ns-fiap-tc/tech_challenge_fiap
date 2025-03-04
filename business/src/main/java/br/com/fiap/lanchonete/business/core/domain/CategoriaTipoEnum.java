package br.com.fiap.lanchonete.business.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoriaTipoEnum {
    BEBIDA(1L, false),
    SOBREMESA(2L, false),
    ACOMPANHAMENTO(3L, true),
    LANCHE(4L, true);

    private final long id;
    private final boolean cozinhar;

}