package br.com.fiap.lanchonete.business.core.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrdemServico {
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