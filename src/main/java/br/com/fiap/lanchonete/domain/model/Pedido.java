package br.com.fiap.lanchonete.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    private Ordem ordem;
    private List<PedidoItem> itens;
    private Pagamento pagamento;
    private Date createdAt;
    private Date updatedAt;
}
