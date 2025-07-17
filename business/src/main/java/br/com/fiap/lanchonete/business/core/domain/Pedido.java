package br.com.fiap.lanchonete.business.core.domain;

import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pedido {
    private Long id;
    private Long clienteId;
    private PedidoStatus status;
    private List<PedidoItem> itens;
    private String pagamentoId;
    private Date createdAt;
    private Date updatedAt;

    public long getTempoEspera() {
        long tempoEspera = 0L;
        if (this.status != PedidoStatus.PRONTO && this.status != PedidoStatus.FINALIZADO) {
            tempoEspera = (Calendar.getInstance().getTimeInMillis() - this.getCreatedAt().getTime()) / 60000;
        }
        return tempoEspera;
    }

    public Double getValorTotal() {
        Double valorTotal = 0.0D;
        if (itens != null && !itens.isEmpty()) {
            valorTotal = itens.stream()
                    .mapToDouble(PedidoItem::getValorTotal)
                    .sum();
        }
        return valorTotal;
    }
}