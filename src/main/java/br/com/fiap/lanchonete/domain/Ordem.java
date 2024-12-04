package br.com.fiap.lanchonete.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_ordem")
public class Ordem {
    @Id
    @GeneratedValue(generator="ordemIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="ordemIdGen", sequenceName="sq_tb_ordem", initialValue=1, allocationSize=1)
    private Long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
