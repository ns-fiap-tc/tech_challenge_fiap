package br.com.fiap.lanchonete.application.device.persistence.entity;

import br.com.fiap.lanchonete.business.core.domain.FormaPagamento;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_pagamento")
public class PagamentoEntity {
    @Id
    @GeneratedValue(generator="pagamentoIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="pagamentoIdGen", sequenceName="sq_tb_pagamento", initialValue=1, allocationSize=1)
    private Long id;

    @OneToOne(mappedBy = "pagamento")
    private PedidoEntity pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "dm_status")
    private PagamentoStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "dm_forma_pagto")
    private FormaPagamento forma;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}