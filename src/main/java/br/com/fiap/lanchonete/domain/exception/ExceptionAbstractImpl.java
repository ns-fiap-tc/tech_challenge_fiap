package br.com.fiap.lanchonete.domain.exception;

import br.com.fiap.lanchonete.domain.model.ValidacaoEnum;
import lombok.Getter;

public class ExceptionAbstractImpl extends RuntimeException implements ExceptionAbstract {
    private ValidacaoEnum validacao;
    @Getter private String[] params;

    @Override
    public Integer getCodigo() {
        return this.validacao.getCodigo();
    }

    @Override
    public String getMensagem() {
        return this.validacao.getDescricao();
    }

    public ExceptionAbstractImpl(ValidacaoEnum validacao){
        super(validacao.getDescricao());
        this.validacao = validacao;
    }

    public ExceptionAbstractImpl(ValidacaoEnum validacao, String... params){
        super(validacao.getDescricao());
        this.validacao = validacao;
        this.params = params;
    }
}
