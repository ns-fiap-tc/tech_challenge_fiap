package br.com.fiap.lanchonete.business.common.dto;

import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdemServicoDto {
    private Long id;
    private Long pedidoId;
    private Long pedidoItemId;
    private String nome;
    private Long produtoId;
    private OrdemServicoStatus status;
    //tempo de preparo do produto em segundos.
    private int tempoPreparo;
    private int quantidade;
    private Date createdAt;
    private Date updatedAt;
}