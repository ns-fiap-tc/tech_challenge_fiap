package br.com.fiap.lanchonete.business.core.exception;

import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;

public class ValidacaoException extends ExceptionAbstractImpl {
    public ValidacaoException(ValidacaoEnum validation){
        super(validation);
    }

    public ValidacaoException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}