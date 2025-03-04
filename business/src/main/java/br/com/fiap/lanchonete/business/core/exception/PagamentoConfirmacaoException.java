package br.com.fiap.lanchonete.business.core.exception;

import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;

public class PagamentoConfirmacaoException extends ExceptionAbstractImpl {

    public PagamentoConfirmacaoException(ValidacaoEnum validation){
        super(validation);
    }

    public PagamentoConfirmacaoException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}