package br.com.fiap.lanchonete.business.core.domain;

public enum ValidacaoEnum {
    ENTRADA_DE_DADOS_INVALIDA(-1, "Existe(m) campo(s) obrigatório(s) não informado(s)."),
    CPF_INVALIDO(-2, "O cpf informado é inválido."),
    CPF_JA_CADASTRADO(-3, "O cpf informado já existe."),
    CLIENTE_NAO_ENCONTRADO(-4, "Não foi possível localizar o cliente."),
    PAGAMENTO_NAO_CONFIRMADO(-5, "O pagamento foi recusado."),
    NAO_IDENTIFICADO(-999, "Ocorreu um problema não identificado.");

    private final Integer codigo;
    private final String descricao;

    public static ValidacaoEnum valueOf(Integer codigo){
        for(ValidacaoEnum val: ValidacaoEnum.values()){
            if(val.codigo.equals(codigo)) return val;
        }
        return null;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    ValidacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}