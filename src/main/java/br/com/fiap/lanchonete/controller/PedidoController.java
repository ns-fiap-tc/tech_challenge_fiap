package br.com.fiap.lanchonete.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Log4j2
@RestController
@Tag(name = "pedido-service")
public class PedidoController {

    @Operation(summary = "Hello World method", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hello World greeting.")
    })
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
