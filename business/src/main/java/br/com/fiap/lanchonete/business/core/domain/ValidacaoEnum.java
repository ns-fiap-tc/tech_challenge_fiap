package br.com.fiap.lanchonete.business.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidacaoEnum {
    ENTRADA_DE_DADOS_INVALIDA(-1, "-1"),
    CPF_INVALIDO(-2, "-2"),
    CPF_JA_CADASTRADO(-3, "-3"),
    CLIENTE_NAO_ENCONTRADO(-4, "-4"),
    PAGAMENTO_NAO_CONFIRMADO(-5, "-5"),
    NAO_IDENTIFICADO(-999, "-999");

    private final Integer codigo;
    private final String descricao;

    public static ValidacaoEnum valueOf(Integer codigo){
        for(ValidacaoEnum val: ValidacaoEnum.values()){
            if(val.codigo.equals(codigo)) return val;
        }
        return null;
    }
}