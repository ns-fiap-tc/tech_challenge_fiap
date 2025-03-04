package br.com.fiap.lanchonete.application.device.rest.exception.handler;

import br.com.fiap.lanchonete.business.common.dto.ErroDto;
import br.com.fiap.lanchonete.business.common.dto.ErrosDto;
import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import br.com.fiap.lanchonete.business.core.exception.ExceptionAbstractImpl;
import br.com.fiap.lanchonete.business.core.exception.ExceptionNotFoundAbstractImpl;
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
public class ValidacaoHandler {

    @ExceptionHandler(ExceptionAbstractImpl.class)
    public ResponseEntity<Object> validacaoHandle(ExceptionAbstractImpl ex){
        if(ex.getCodigo().equals(ValidacaoEnum.NAO_IDENTIFICADO.getCodigo())){
            log.error("Ocorreu um erro interno",ex);
            return new ResponseEntity<>(
                    ErroDto.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }else {
            List<ErroDto> erros = new ArrayList<>();
            erros.add(ErroDto.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build());
            log.warn(ex.getMensagem());
            return new ResponseEntity<>(
                    ErrosDto.builder().erros(erros).build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ExceptionNotFoundAbstractImpl.class)
    public ResponseEntity<Object> validacaoHandleNotFound(ExceptionNotFoundAbstractImpl ex){
        List<ErroDto> erros = new ArrayList<>();
        erros.add(ErroDto.builder().codigo(ex.getCodigo()).mensagem(ex.getMensagem()).build());
        log.warn(ex.getMensagem());
        return new ResponseEntity<>(
                    ErrosDto.builder().erros(erros).build(),
                    HttpStatus.NOT_FOUND);
    }

}