package br.com.fiap.lanchonete.adapter.input.exception;

import br.com.fiap.lanchonete.domain.model.ValidacaoEnum;
import lombok.Getter;

public class ValidacaoRuntimeException extends RuntimeException {
    @Getter private String descricao;

    public ValidacaoRuntimeException(ValidacaoEnum validation){
        super(validation.getDescricao());
        this.descricao = validation.getDescricao();
    }

    public ValidacaoRuntimeException(String msg, Throwable cause){
        super(msg,cause);
        this.descricao = msg;
    }

    public ValidacaoRuntimeException(String msg){
        super(msg);
        this.descricao = msg;
    }
}
