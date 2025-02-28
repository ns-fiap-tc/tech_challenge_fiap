package br.com.fiap.lanchonete.application.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_pedido_item")
public class PedidoItemEntity {
    @Id
    @GeneratedValue(generator="pedidoItemIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="pedidoItemIdGen", sequenceName="sq_tb_pedido_item", initialValue=1, allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_pedido", referencedColumnName = "id", nullable = false)
    private PedidoEntity entity;

    @Column(name = "nome")
    private String nome;

    @Column(name = "id_produto")
    private Long produtoId;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "ds_obs")
    private String observacoes;
}