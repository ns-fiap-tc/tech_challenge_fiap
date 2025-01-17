package br.com.fiap.lanchonete.domain.exception;

import br.com.fiap.lanchonete.domain.model.ValidacaoEnum;

public class PagamentoConfirmacaoException extends ExceptionAbstractImpl {

    public PagamentoConfirmacaoException(ValidacaoEnum validation){
        super(validation);
    }

    public PagamentoConfirmacaoException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}
