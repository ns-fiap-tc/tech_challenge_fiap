package br.com.fiap.lanchonete.business.core.exception;

import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import lombok.Getter;

public class ExceptionAbstractImpl extends RuntimeException implements ExceptionAbstract {
    private final ValidacaoEnum validacao;
    @Getter private String[] params;

    public ExceptionAbstractImpl(ValidacaoEnum validacao){
        super(validacao.getDescricao());
        this.validacao = validacao;
    }

    public ExceptionAbstractImpl(ValidacaoEnum validacao, String... params){
        super(validacao.getDescricao());
        this.validacao = validacao;
        this.params = params;
    }

    @Override
    public Integer getCodigo() {
        return this.validacao.getCodigo();
    }

    @Override
    public String getMensagem() {
        return this.validacao.getDescricao();
    }
}