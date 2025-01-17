package br.com.fiap.lanchonete.domain.exception;

public class PagamentoConfirmacaoException extends RuntimeException {

    public PagamentoConfirmacaoException() {
        super();
    }

    public PagamentoConfirmacaoException(String message) {
        super(message);
    }

    public PagamentoConfirmacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
