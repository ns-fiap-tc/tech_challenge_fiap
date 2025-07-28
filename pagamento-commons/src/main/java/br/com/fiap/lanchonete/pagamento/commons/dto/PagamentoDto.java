package br.com.fiap.lanchonete.pagamento.commons.dto;

import br.com.fiap.lanchonete.pagamento.commons.domain.FormaPagamento;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
public class PagamentoDto {
    private String id;
    @NotNull
    private Long pedidoId;
    @NotNull
    private PagamentoStatus status;
    @NotNull
    private FormaPagamento forma;
    @NotNull
    private BigDecimal valor;
    private Date createdAt;
    private Date updatedAt;
}