package br.com.fiap.lanchonete.business.common.dto;

import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
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
    private Pagamento pagamento;
    private Long tempoEspera;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}