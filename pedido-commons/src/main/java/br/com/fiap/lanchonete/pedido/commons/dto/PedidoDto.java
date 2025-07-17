package br.com.fiap.lanchonete.pedido.commons.dto;

import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
    @Null
    private Long id;
    @NotNull
    private PedidoStatus status;
    @NotNull
    private Long clienteId;
    @NotNull
    private List<PedidoItemDto> itens;
    @NotNull
    private PagamentoDto pagamentoDto;
    private String pagamentoId;
    private Long tempoEspera;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}