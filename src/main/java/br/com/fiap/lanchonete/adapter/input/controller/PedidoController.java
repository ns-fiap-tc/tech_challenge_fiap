package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.PedidoDto;
import br.com.fiap.lanchonete.adapter.input.mapper.PedidoMapper;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import br.com.fiap.lanchonete.domain.usecase.PedidoUseCases;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pedido-service/v1")
@Tag(name = "pedido-service")
public class PedidoController implements PedidoApi {

    public static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoUseCases service;

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
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody PedidoDto pedidoDto)
    {
        PedidoDto dtoNew = MAPPER.toDto(service.save(MAPPER.toDomain(pedidoDto)));
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .buildAndExpand(dtoNew.getId())
                                .toUri())
                .build();
    }

    @Operation(summary = "Pedido a ser atualizado", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto pedido nao encontrado")
    })
    @PutMapping("/save/{id}")
    public ResponseEntity<PedidoDto> update(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser atualizado")
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
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Void> updateStatus(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "id of book to be searched")
            @Valid @RequestBody PedidoStatus pedidoStatus)
    {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Operation(summary = "Lista todos os pedidos.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<PedidoDto>> findAll() {
        return ResponseEntity.ok(MAPPER.map(service.findAll()));
    }

    @Override
    @Operation(summary = "Busca um pedido pelo id do cliente", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = @Content) })
    @GetMapping("/findByCliente/{id}")
    public ResponseEntity<List<PedidoDto>> findByCliente(
            @NotNull @PathVariable(value = "id") long clienteId) {
        List<PedidoDto> list = null;
        return ResponseEntity.ok(list);
    }

    @Override
    @Operation(summary = "Busca um pedido pelo numero", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = @Content) })
    @GetMapping("/findById/{id}")
    public ResponseEntity<PedidoDto> findById(
            @NotNull @PathVariable(value = "id") long id)
    {
        PedidoDto pedidoDto = null;
        return ResponseEntity.ok(pedidoDto);
    }
}
