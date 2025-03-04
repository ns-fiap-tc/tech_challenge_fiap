package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.device.rest.CategoriaApi;
import br.com.fiap.lanchonete.business.adapter.controller.CategoriaController;
import br.com.fiap.lanchonete.business.common.dto.CategoriaDto;
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
@RequestMapping(path = "/categoria-service/v1")
@Tag(name = "categoria-service")
public class CategoriaApiImpl implements CategoriaApi {
    private final CategoriaController controller;

    @Override
    @Operation(summary = "Criar uma nova categoria. Retorna o id do objeto criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody CategoriaDto categoriaDto)
    {
        CategoriaDto dtoNew = controller.create(categoriaDto);
        return ResponseEntity.ok(dtoNew.getId());
    }

    @Override
    @Operation(summary = "Atualizar uma categoria existente. Retorna o objeto atualizado.", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @PutMapping("/save/{id}")
    public ResponseEntity<CategoriaDto> update(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser atualizado")
            @Valid @RequestBody final CategoriaDto categoriaDto)
    {
        categoriaDto.setId(id);
        return ResponseEntity.ok(controller.update(categoriaDto));
    }

    @Override
    @Operation(summary = "Busca a categoria pelo id. Retorna o objeto, caso o encontre.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoriaDto> findById(
            @NotNull @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(controller.findById(id));
    }

    @Override
    @Operation(summary = "Lista todas as categorias.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return ResponseEntity.ok(controller.findAll());
    }

    @Override
    @Operation(summary = "Busca a categoria pelo nome. Retorna o objeto, caso o encontre.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<CategoriaDto>> findByNome(
            @NotNull @PathVariable(value = "nome") String nome) {
        return ResponseEntity.ok(controller.findByNome(nome));
    }

    @Override
    @Operation(summary = "Exclui a categoria a partir do ID dela.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(
            @NotNull @PathVariable(value = "id") Long id) {
        controller.deleteById(id);
        return ResponseEntity.ok().build();
    }
}