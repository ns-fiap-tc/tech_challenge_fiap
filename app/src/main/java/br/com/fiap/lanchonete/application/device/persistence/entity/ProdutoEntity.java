package br.com.fiap.lanchonete.application.device.persistence.entity;

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
@Table(name = "tb_produto")
public class ProdutoEntity {

    @Id
    @GeneratedValue(generator="produtoIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="produtoIdGen", sequenceName="sq_tb_produto", initialValue=1, allocationSize=1)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "tempo_preparo")
    private int tempoPreparo;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}