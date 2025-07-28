package br.com.fiap.lanchonete.business.core.domain;


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
public class PedidoItem {
    private Long id;
    private String nome;
    private Long produtoId;
    private int quantidade;
    private Double valorUnitario;
    private String observacoes;

    public Double getValorTotal() {
        return valorUnitario * quantidade;
    }
}