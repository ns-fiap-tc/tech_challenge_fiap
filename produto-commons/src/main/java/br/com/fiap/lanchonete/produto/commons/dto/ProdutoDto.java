package br.com.fiap.lanchonete.produto.commons.dto;

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
    @NotNull
    private int tempoPreparo;
    @NotNull
    private String fotoUrl;
    @NotNull
    private Double preco;
    @NotNull
    private String descricao;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}