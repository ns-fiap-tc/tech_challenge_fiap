package br.com.fiap.lanchonete.adapter.input.dto;

import br.com.fiap.lanchonete.domain.model.FormaPagamento;
import br.com.fiap.lanchonete.domain.model.PagamentoStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private PagamentoStatus status;
    private FormaPagamento forma;
    private Date createdAt;
    private Date updatedAt;
}
