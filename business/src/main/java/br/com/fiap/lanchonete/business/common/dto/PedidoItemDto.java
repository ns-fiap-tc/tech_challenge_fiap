package br.com.fiap.lanchonete.business.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoItemDto {
    @Null
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private Long produtoId;
    @NotNull
    private int quantidade;
    @NotNull
    private Double valorUnitario;
    @Null
    private String observacoes;
}