package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.device.rest.ClienteApi;
import br.com.fiap.lanchonete.business.adapter.controller.ClienteController;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
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

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/cliente-service/v1")
@Tag(name = "cliente-service")
public class ClienteApiImpl implements ClienteApi {
    private final ClienteController controller;

    @Override
    @Operation(summary = "Criar um novo cliente. Retorna o id do objeto criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody ClienteDto clienteDto)
    {
        ClienteDto dtoNew = controller.create(clienteDto);
        return ResponseEntity.ok(dtoNew.getId());
    }

    @Override
    @Operation(summary = "Atualizar um cliente existente. Retorna o objeto atualizado.", method = "PUT")
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
        clienteDto.setId(id);
        return ResponseEntity.ok(controller.update(clienteDto));
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
        ClienteDto dto = controller.findByCpf(cpf);
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
        return ResponseEntity.ok(controller.findAll());
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
        ClienteDto dto = controller.findByEmail(email);
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
        controller.deleteByEmail(email);
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
        controller.deleteByCpf(cpf);
        return ResponseEntity.ok().build();
    }
}