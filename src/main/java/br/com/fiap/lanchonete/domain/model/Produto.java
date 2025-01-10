package br.com.fiap.lanchonete.domain.model;

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
    private Long categoriaId;
    private Date createdAt;
    private Date updatedAt;
}
