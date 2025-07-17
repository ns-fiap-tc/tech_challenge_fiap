package br.com.fiap.lanchonete.categoria.core.domain;

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
public class Categoria {
    private Long id;
    private String nome;
    private CategoriaTipoEnum tipo;
    private Date createdAt;
    private Date updatedAt;
}