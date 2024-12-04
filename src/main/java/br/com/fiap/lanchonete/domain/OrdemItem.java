package br.com.fiap.lanchonete.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_ordem_item")
public class OrdemItem {
    @Id
    @GeneratedValue(generator="ordemItemIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="ordemItemIdGen", sequenceName="sq_tb_ordem_item", initialValue=1, allocationSize=1)
    private Long id;
}
