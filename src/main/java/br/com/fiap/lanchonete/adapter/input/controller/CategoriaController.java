package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.CategoriaDto;
import br.com.fiap.lanchonete.adapter.input.mapper.CategoriaMapper;
import br.com.fiap.lanchonete.domain.usecase.CategoriaUseCases;
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
@RequestMapping(path = "/categoria-service/v1")
@Tag(name = "categoria-service")
public class CategoriaController implements CategoriaApi {
    private static final CategoriaMapper MAPPER = CategoriaMapper.INSTANCE;
    private final CategoriaUseCases service;

    @Override
    @Operation(summary = "Criar uma nova categoria.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody CategoriaDto categoriaDto)
    {
        CategoriaDto dtoNew = MAPPER.toDto(service.save(MAPPER.toDomain(categoriaDto)));
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .buildAndExpand(dtoNew.getId())
                                .toUri())
                .build();
    }

    @Override
    @Operation(summary = "Atualizar uma categoria existente.", method = "PUT")
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
        CategoriaDto dto = MAPPER.toDto(service.save(MAPPER.toDomain(categoriaDto)));
        return ResponseEntity.ok(dto);
    }

    @Override
    @Operation(summary = "Busca a categoria pelo id.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<CategoriaDto> findById(
            @NotNull @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(MAPPER.toDto(service.findById(id)));
    }

    @Override
    @Operation(summary = "Lista todas as categorias.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return ResponseEntity.ok(MAPPER.map(service.findAll()));
    }

    @Override
    @Operation(summary = "Busca a categoria pelo nome.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<CategoriaDto>> findByNome(
            @NotNull @PathVariable(value = "nome") String nome) {
        return ResponseEntity.ok(MAPPER.map(service.findByNome(nome)));
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
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
