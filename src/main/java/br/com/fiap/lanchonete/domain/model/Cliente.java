package br.com.fiap.lanchonete.domain.model;

import jakarta.persistence.Column;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {
    private Long id;
    private String nome;
    private String cfp;
    private String email;
    private String celular;
    private Date createdAt;
    private Date updatedAt;
}
