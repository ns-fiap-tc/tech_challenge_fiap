package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ClienteDto;
import br.com.fiap.lanchonete.adapter.input.mapper.ClienteMapper;
import br.com.fiap.lanchonete.domain.usecase.ClienteUseCases;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(path = "/cliente-service/v1")
@Tag(name = "cliente-service")
public class ClienteController implements ClienteApi {

    private static final ClienteMapper MAPPER = ClienteMapper.INSTANCE;
    private final ClienteUseCases service;

    @Override
    @Operation(summary = "Criar um novo cliente.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody ClienteDto clienteDto)
    {
        ClienteDto dtoNew = MAPPER.toDto(service.save(MAPPER.toDomain(clienteDto)));
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .buildAndExpand(dtoNew.getId())
                                .toUri())
                .build();
    }

    @Override
    @Operation(summary = "Atualizar um cliente existente.", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @PutMapping("/save/{id}")
    public ResponseEntity<ClienteDto> update(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser atualizado")
            @Valid @RequestBody ClienteDto clienteDto)
    {
        ClienteDto dto = MAPPER.toDto(service.save(MAPPER.toDomain(clienteDto)));
        return ResponseEntity.ok(dto);
    }

    @Override
    @Operation(summary = "Busca o cliente pelo CPF.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByCpf/{cpf}")
    public ResponseEntity<ClienteDto> findByCpf(
            @NotNull @PathVariable(value = "cpf") String cpf)
    {
        ClienteDto dto = MAPPER.toDto(service.findByCpf(cpf));
        return ResponseEntity.ok(dto);
    }

    @Override
    @Operation(summary = "Lista todos os clientes.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<ClienteDto>> findAll() {
        return ResponseEntity.ok(MAPPER.map(service.findAll()));
    }

    @Override
    @Operation(summary = "Busca o cliente pelo email.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<ClienteDto> findByEmail(
            @NotNull @PathVariable(value = "email") String email)
    {
        ClienteDto dto = MAPPER.toDto(service.findByEmail(email));
        return ResponseEntity.ok(dto);
    }

    @Override
    @Operation(summary = "Exclui o cliente a partir do email dele.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<Void> deleteByEmail(
            @NotNull @PathVariable(value = "email") String email)
    {
        service.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    @Override
    @Operation(summary = "Exclui o cliente a partir do CPF dele.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @DeleteMapping("/deleteByCpf/{cpf}")
    public ResponseEntity<Void> deleteByCpf(
            @NotNull @PathVariable(value = "cpf") String cpf)
    {
        service.deleteByCpf(cpf);
        return ResponseEntity.ok().build();
    }
}
