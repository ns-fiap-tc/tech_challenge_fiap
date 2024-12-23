package br.com.fiap.lanchonete.adapter.input.dto;

import br.com.fiap.lanchonete.domain.model.OrdemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoItemDto {
    private Long id;
    private String nome;
    private boolean isOrdemItem;
    private OrdemStatus status;
    private String observacoes;
}
