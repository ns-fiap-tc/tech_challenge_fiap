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
public class Categoria {
    private Long id;
    private String nome;
    private int cozinhar;
    private Date createdAt;
    private Date updatedAt;

    public boolean cozinhar() {
        return cozinhar == 1;
    }
}
