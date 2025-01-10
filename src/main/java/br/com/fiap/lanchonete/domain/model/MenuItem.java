package br.com.fiap.lanchonete.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuItem {
    private Long id;
    private String nome;
    private List<Produto> produtos;
}
