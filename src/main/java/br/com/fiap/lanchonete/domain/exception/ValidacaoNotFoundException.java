package br.com.fiap.lanchonete.domain.exception;

import br.com.fiap.lanchonete.domain.model.ValidacaoEnum;

public class ValidacaoNotFoundException extends ExceptionNotFoundAbstractImpl {
    public ValidacaoNotFoundException(ValidacaoEnum validation){
        super(validation);
    }

    public ValidacaoNotFoundException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}
