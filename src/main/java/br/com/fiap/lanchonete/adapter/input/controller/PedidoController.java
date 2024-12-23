package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.PedidoDto;
import br.com.fiap.lanchonete.domain.model.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CommonsLog
@RequiredArgsConstructor
@Validated
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

    @Operation(summary = "Criar um novo pedido", method = "POST")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Criacao realizada com sucesso.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/create")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody PedidoDto pedidoDto)
    {
        Long id = null;
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .buildAndExpand(id)
                                .toUri())
                .build();
    }

    @Operation(summary = "Pedido a ser atualizado", method = "PUT")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Objeto atualizado com sucesso.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Objeto pedido nao encontrado")
    })
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "id of book to be searched")
            @Valid @RequestBody PedidoDto pedidoDto)
    {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Metodo que atualiza o status do pedido.", method = "PUT")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do pedido atualizado.",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado")
    })
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "id of book to be searched")
            @Valid @RequestBody PedidoDto pedidoDto)
    {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Busca um pedido pelo numero", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado",
                    content = {
                            @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = @Content) })
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "id do pedido a ser buscado")
            @PathVariable long id)
    {
        PedidoDto pedidoDto = null;
        return ResponseEntity.ok(pedidoDto);
    }
}
