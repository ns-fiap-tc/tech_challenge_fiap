package br.com.fiap.lanchonete.adapter.input.dto;

import br.com.fiap.lanchonete.domain.model.Ordem;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDto {
    private Long id;
    private PedidoStatus status;
    private Ordem ordem;
    private List<PedidoItemDto> itens;
    private Date createdAt;
    private Date updatedAt;
}
