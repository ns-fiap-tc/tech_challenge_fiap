package br.com.fiap.lanchonete.business.core.exception.handler;

import br.com.fiap.lanchonete.business.common.dto.ErroDto;
import br.com.fiap.lanchonete.business.common.dto.ErrosDto;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoRuntimeException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(ValidacaoRuntimeException.class)
    public ResponseEntity<Object> validacaoHandle(ValidacaoRuntimeException ex){
        List<ErroDto> erros = new ArrayList<>();
        erros.add(ErroDto.builder().codigo(-999).mensagem(ex.getDescricao()).build());
        log.error("Ocorreu um erro interno",ex);
        return new ResponseEntity<>(
                    ErrosDto.builder().erros(erros).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

}