package br.com.fiap.lanchonete.business.core.exception;

import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;

public class ValidacaoNotFoundException extends ExceptionNotFoundAbstractImpl {
    public ValidacaoNotFoundException(ValidacaoEnum validation){
        super(validation);
    }

    public ValidacaoNotFoundException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}