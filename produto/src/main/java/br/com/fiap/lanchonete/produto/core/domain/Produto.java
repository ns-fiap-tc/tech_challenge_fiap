package br.com.fiap.lanchonete.produto.core.domain;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    private Long id;
    private String nome;
    private CategoriaTipoEnum categoriaTipoEnum;
    //tempo de preparo do produto em segundos.
    private int tempoPreparo;
    private String fotoUrl;
    private Double preco;
    private String descricao;
    //private int estoque;
    private Date createdAt;
    private Date updatedAt;
}