package br.com.fiap.lanchonete.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdemItem {
    private Long id;
    private OrdemItemStatus ordemItemStatus;
}
