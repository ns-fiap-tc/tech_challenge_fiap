package br.com.fiap.lanchonete.business.core.exception;

import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import lombok.Getter;

public class ExceptionNotFoundAbstractImpl extends RuntimeException implements ExceptionAbstract {
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

    public ExceptionNotFoundAbstractImpl(ValidacaoEnum validacao){
        super(validacao.getDescricao());
        this.validacao = validacao;
    }

    public ExceptionNotFoundAbstractImpl(ValidacaoEnum validacao, String... params){
        super(validacao.getDescricao());
        this.validacao = validacao;
        this.params = params;
    }
}