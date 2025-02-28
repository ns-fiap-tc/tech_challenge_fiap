package br.com.fiap.lanchonete.business.core.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoItem {
    private Long id;
    private String nome;
    private Long produtoId;
    private int quantidade;
    private String observacoes;
}