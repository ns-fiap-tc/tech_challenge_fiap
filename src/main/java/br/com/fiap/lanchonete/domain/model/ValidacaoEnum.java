package br.com.fiap.lanchonete.domain.model;

import lombok.Getter;

@Getter
public enum ValidacaoEnum implements IEnumLabel<ValidacaoEnum> {
    ENTRADA_DE_DADOS_INVALIDA(-1),
    CPF_INVALIDO(-2),
    CPF_JA_CADASTRADO(-3),
    CLIENTE_NAO_ENCONTRADO(-4),
    PAGAMENTO_NAO_CONFIRMADO(-5),
    NAO_IDENTIFICADO(-999);

    private Integer codigo;

    ValidacaoEnum(Integer codigo){
        this.codigo = codigo;
    }

    public static ValidacaoEnum valueOf(Integer codigo){
        for(ValidacaoEnum val: ValidacaoEnum.values()){
            if(val.codigo.equals(codigo)) return val;
        }
        return null;
    }
}
