package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.device.rest.PedidoApi;
import br.com.fiap.lanchonete.application.device.rest.filter.RequestContext;
import br.com.fiap.lanchonete.business.adapter.controller.ClienteController;
import br.com.fiap.lanchonete.business.adapter.controller.PedidoController;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/pedido-service/v1")
@Tag(name = "pedido-service")
public class PedidoApiImpl implements PedidoApi {
    private final PedidoController controller;
    private final ClienteController clienteController;

    @Override
    @Operation(summary = "Criar um novo pedido. Retorna o numero do pedido criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<PedidoDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody PedidoDto pedidoDto)
    {
        //faz a leitura do CPF que foi incluido no ThreadLocal, pelo Filter.
        String clienteCpf = RequestContext.getCurrentToken();
        if (StringUtils.isNotBlank(clienteCpf)) {
            ClienteDto cliente = clienteController.findByCpf(clienteCpf);
            if (cliente != null) {
                pedidoDto.setClienteId(cliente.getId());
                //apos utilizar o CPF existente no ThreadLocal, remover o valor.
                RequestContext.clear();
            }
        }
        PedidoDto dtoNew = controller.create(pedidoDto);
        return ResponseEntity.ok(dtoNew);
    }

    @Override
    @Operation(summary = "Pedido a ser atualizado. Retorna o objeto alterado.", method = "PUT")
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
        pedidoDto.setId(id);
        return ResponseEntity.ok(controller.update(pedidoDto));
    }

    @Override
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
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "id do pedido a ser atualizado")
            @Valid @RequestBody PedidoStatus pedidoStatus)
    {
        controller.updateStatus(id, pedidoStatus);
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
        return ResponseEntity.ok(controller.findAllOrdered());
    }

    @Override
    @Operation(summary = "Lista os pedidos por status. Utilizado para apresentar aos clientes os pedidos que estão prontos para serem retirados e que estão em preparação.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<PedidoDto>> findByStatus(
            @NotNull @PathVariable(value = "status") PedidoStatus status)
    {
        return ResponseEntity.ok(controller.findByStatus(status));
    }

    @Override
    @Operation(summary = "Busca os pedidos a partir do id do cliente.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = @Content) })
    @GetMapping("/findByCliente/{id}")
    public ResponseEntity<List<PedidoDto>> findByCliente(
            @NotNull @PathVariable(value = "id") long clienteId)
    {
        return ResponseEntity.ok(controller.findByCliente(clienteId));
    }

    @Override
    @Operation(summary = "Busca um pedido pelo seu numero. Retorna o objeto, caso o encontre.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "400", description = "Numero de pedido invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = @Content) })
    @GetMapping("/findById/{id}")
    public ResponseEntity<PedidoDto> findById(
            @NotNull @PathVariable(value = "id") long id)
    {
        PedidoDto pedidoDto = controller.findById(id);
        return ResponseEntity.ok(pedidoDto);
    }
}