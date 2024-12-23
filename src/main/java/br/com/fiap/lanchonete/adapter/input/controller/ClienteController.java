package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.mapper.ClienteMapper;
import br.com.fiap.lanchonete.application.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@Tag(name = "cliente-service")
public class ClienteController {

    private final ClienteService service;
    private ClienteMapper mapper = ClienteMapper.INSTANCE;
}
