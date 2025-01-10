package br.com.fiap.lanchonete.adapter.input.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoDto {
    @Null
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private Long categoriaId;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}
