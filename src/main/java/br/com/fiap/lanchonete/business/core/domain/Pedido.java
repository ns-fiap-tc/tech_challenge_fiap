package br.com.fiap.lanchonete.business.core.domain;

import java.util.Calendar;
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
public class Pedido {
    private Long id;
    private Long clienteId;
    private PedidoStatus status;
    private List<PedidoItem> itens;
    private Pagamento pagamento;
    private Date createdAt;
    private Date updatedAt;

    public long getTempoEspera() {
        long tempoEspera = 0L;
        if (this.status != PedidoStatus.PRONTO && this.status != PedidoStatus.FINALIZADO) {
            tempoEspera = (Calendar.getInstance().getTimeInMillis() - this.getCreatedAt().getTime()) / 60000;
        }
        return tempoEspera;
    }
}