package br.com.fiap.lanchonete.application.device.rest.client;

import br.com.fiap.lanchonete.business.adapter.controller.CategoriaServiceClient;
import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "categoria-service-client")
public interface CategoriaServiceClientImpl extends CategoriaServiceClient {

    @PostMapping("/save")
    CategoriaDto create(@NotNull CategoriaDto categoriaDto);

    @PutMapping("/save/{id}")
    CategoriaDto update(
            @NotNull @PathVariable(value = "id") long id,
            @NotNull CategoriaDto categoriaDto);

    @DeleteMapping("/deleteById/{id}")
    void deleteById(@NotNull @PathVariable(value = "id") Long id);

    @GetMapping("/findAll")
    List<CategoriaDto> findAll();

    @GetMapping("/findById/{id}")
    CategoriaDto findById(@NotNull @PathVariable(value = "id") Long id);

    @GetMapping("/findByNome/{nome}")
    List<CategoriaDto> findByNome(@NotNull @PathVariable(value = "nome") String nome);
}