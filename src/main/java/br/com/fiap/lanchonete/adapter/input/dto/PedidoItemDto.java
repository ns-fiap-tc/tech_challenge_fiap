package br.com.fiap.lanchonete.adapter.input.dto;

import br.com.fiap.lanchonete.domain.model.OrdemStatus;
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
    private boolean isOrdemItem;
    @NotNull
    private OrdemStatus status;
    @Null
    private String observacoes;
}
