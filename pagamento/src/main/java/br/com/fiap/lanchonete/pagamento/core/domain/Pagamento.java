package br.com.fiap.lanchonete.pagamento.core.domain;

import br.com.fiap.lanchonete.pagamento.commons.domain.FormaPagamento;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import java.util.Date;
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
public class Pagamento {
    private String id;
    private Long pedidoId;
    private PagamentoStatus status;
    private FormaPagamento forma;
    private Double valor;
    private Date createdAt;
    private Date updatedAt;
}