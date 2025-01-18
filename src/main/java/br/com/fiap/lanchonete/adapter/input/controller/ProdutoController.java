package br.com.fiap.lanchonete.adapter.input.controller;

import br.com.fiap.lanchonete.adapter.input.dto.ProdutoDto;
import br.com.fiap.lanchonete.adapter.input.mapper.ProdutoMapper;
import br.com.fiap.lanchonete.domain.usecase.ProdutoUseCases;
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
@RequestMapping(path = "/produto-service/v1")
@Tag(name = "produto-service")
public class ProdutoController implements ProdutoApi {

    private static final ProdutoMapper MAPPER = ProdutoMapper.INSTANCE;
    private final ProdutoUseCases service;

    @Override
    @Operation(summary = "Criar um novo produto. Retorna o id do objeto criado.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/save")
    public ResponseEntity<Long> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody ProdutoDto produtoDto) {
        ProdutoDto dtoNew = MAPPER.toDto(service.save(MAPPER.toDomain(produtoDto)));
        return ResponseEntity.ok(dtoNew.getId());
    }

    @Override
    @Operation(summary = "Atualizar um produto existente. Retorna o objeto atualizado.", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @PutMapping("/save/{id}")
    public ResponseEntity<ProdutoDto> update(
            @NotNull @PathVariable(value = "id") long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser atualizado")
            @Valid @RequestBody ProdutoDto produtoDto)
    {
        produtoDto.setId(id);
        return ResponseEntity.ok(MAPPER.toDto(service.save(MAPPER.toDomain(produtoDto))));
    }

    @Override
    @Operation(summary = "Busca os produtos a partir ID.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<ProdutoDto> findById(
            @NotNull @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(MAPPER.toDto(service.findById(id)));
    }

    @Override
    @Operation(summary = "Busca os produtos a partir de um determinado nome ou parte dele.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<ProdutoDto>> findByNome(
            @NotNull @PathVariable(value = "nome") String nome) {
        return ResponseEntity.ok(MAPPER.map(service.findByNome(nome)));
    }

    @Override
    @Operation(summary = "Lista todos os produtos.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Bad Request.")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<ProdutoDto>> findAll() {
        return ResponseEntity.ok(MAPPER.map(service.findAll()));
    }

    @Override
    @Operation(summary = "Busca os produtos de acordo com a categoria informada.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Objeto nao encontrado.")
    })
    @GetMapping("/findByCategoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDto>> findByCategoria(
            @NotNull @PathVariable(value = "categoriaId") Long categoriaId) {
        return ResponseEntity.ok(MAPPER.map(service.findByCategoria(categoriaId)));
    }

    @Override
    @Operation(summary = "Exclui o produto a partir do id dele.", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operacao realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Bad Request.")
    })
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(
            @NotNull @PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
