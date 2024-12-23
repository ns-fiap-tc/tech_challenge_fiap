package br.com.fiap.lanchonete.adapter.output.persistence.entity;

import br.com.fiap.lanchonete.domain.model.OrdemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @JoinColumn(name="id_pedido")
    private PedidoEntity entity;

    @Column(name = "nome")
    private String nome;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "is_ordem_it")
    private boolean isOrdemItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "dm_status")
    private OrdemStatus status;

    @Column(name = "ds_obs")
    private String observacoes;
}
