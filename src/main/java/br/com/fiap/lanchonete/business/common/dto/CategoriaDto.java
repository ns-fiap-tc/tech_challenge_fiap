package br.com.fiap.lanchonete.business.common.dto;

import br.com.fiap.lanchonete.business.core.domain.CategoriaTipoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDto {
    @Null
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private CategoriaTipoEnum tipo;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}