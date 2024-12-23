package br.com.fiap.lanchonete.domain.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ordem {
    private Long id;
    private List<OrdemItem> itens;
    private Date createdAt;
    private Date updatedAt;
}
