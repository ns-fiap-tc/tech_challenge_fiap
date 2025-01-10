package br.com.fiap.lanchonete.adapter.output.persistence.entity;

import br.com.fiap.lanchonete.domain.model.Pagamento;
import br.com.fiap.lanchonete.domain.model.PedidoStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(generator="pedidoIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="pedidoIdGen", sequenceName="sq_tb_pedido", initialValue=1, allocationSize=1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dm_status")
    private PedidoStatus status;

    @Column(name = "id_cliente")
    private Long clienteId;

    @Column(name = "id_ordem")
    private Long ordemId;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<PedidoItemEntity> itens;

    @Column(name = "id_pagto")
    private Long pagamentoId;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
