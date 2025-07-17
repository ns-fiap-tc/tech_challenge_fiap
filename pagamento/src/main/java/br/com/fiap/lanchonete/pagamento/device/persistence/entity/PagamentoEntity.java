package br.com.fiap.lanchonete.pagamento.device.persistence.entity;

import br.com.fiap.lanchonete.pagamento.commons.domain.FormaPagamento;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Pagamento")
public class PagamentoEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private Long pedidoId;

    private PagamentoStatus status;

    private FormaPagamento forma;

    private Double valor;

    private Date createdAt;

    private Date updatedAt;
}