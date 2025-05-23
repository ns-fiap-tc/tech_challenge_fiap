package br.com.fiap.lanchonete.business.core.domain;

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
    private Long id;
    private PagamentoStatus status;
    private FormaPagamento forma;
    private Double valor;
    private Date createdAt;
    private Date updatedAt;
}