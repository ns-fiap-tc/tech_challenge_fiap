package br.com.fiap.lanchonete.application.device.rest.exception.handler;

public class TransactionalException extends RuntimeException {

    public TransactionalException(Throwable t) {
        super(t);
    }
}