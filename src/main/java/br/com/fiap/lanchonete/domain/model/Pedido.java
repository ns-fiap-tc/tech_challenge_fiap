package br.com.fiap.lanchonete.domain.model;

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
}
