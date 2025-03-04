package br.com.fiap.lanchonete.application.device.persistence.entity;

import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_ordem_servico")
public class OrdemServicoEntity {
    @Id
    @GeneratedValue(generator="osIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="osIdGen", sequenceName="sq_tb_ordem_servico", initialValue=1, allocationSize=1)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "pedido_item_id")
    private Long pedidoItemId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "produto_id")
    private Long produtoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "dm_status")
    private OrdemServicoStatus status;

    @Column(name = "tempo_preparo")
    private int tempoPreparo;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}