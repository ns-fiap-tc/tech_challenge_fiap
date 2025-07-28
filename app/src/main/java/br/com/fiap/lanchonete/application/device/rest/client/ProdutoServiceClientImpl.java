package br.com.fiap.lanchonete.application.device.rest.client;

import br.com.fiap.lanchonete.business.adapter.controller.ProdutoServiceClient;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "produto-service-client")
public interface ProdutoServiceClientImpl extends ProdutoServiceClient {

    @PostMapping("/save")
    ProdutoDto create(@NotNull ProdutoDto produtoDto);

    @PutMapping("/save/{id}")
    ProdutoDto update(
            @NotNull @PathVariable(value = "id") long id,
            @NotNull ProdutoDto produtoDto);

    @GetMapping("/findById/{id}")
    ProdutoDto findById(@NotNull @PathVariable(value = "id") Long id);

    @GetMapping("/findByNome/{nome}")
    List<ProdutoDto> findByNome(@NotNull @PathVariable(value = "nome") String nome);

    @GetMapping("/findAll")
    List<ProdutoDto> findAll();

    @GetMapping("/findByCategoria/{categoriaId}")
    List<ProdutoDto> findByCategoria(@NotNull @PathVariable(value = "categoriaId") Long categoriaId);

    @DeleteMapping("/deleteById/{id}")
    void deleteById(@NotNull @PathVariable(value = "id") Long id);
}