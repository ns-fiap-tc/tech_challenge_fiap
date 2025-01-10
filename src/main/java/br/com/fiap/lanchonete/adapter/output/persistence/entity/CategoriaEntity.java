package br.com.fiap.lanchonete.adapter.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tb_categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(generator="categoriaIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="categoriaIdGen", sequenceName="sq_tb_categoria", initialValue=1, allocationSize=1)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cozinhar")
    private int cozinhar;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public boolean cozinhar() {
        return cozinhar == 1;
    }
}
