package br.com.fiap.lanchonete.business.core.domain;

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