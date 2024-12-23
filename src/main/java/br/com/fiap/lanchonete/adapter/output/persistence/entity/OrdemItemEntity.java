package br.com.fiap.lanchonete.adapter.output.persistence.entity;

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
@Table(name = "tb_ordem_item")
public class OrdemItemEntity {
    @Id
    @GeneratedValue(generator="ordemItemIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="ordemItemIdGen", sequenceName="sq_tb_ordem_item", initialValue=1, allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_ordem")
    private OrdemEntity ordemEntity;
}
