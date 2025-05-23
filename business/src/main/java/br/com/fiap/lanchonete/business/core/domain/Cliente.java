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
public class Cliente {
    public static final Long CLIENTE_NAO_IDENTIFICADO = -1L;

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String celular;
    private Date createdAt;
    private Date updatedAt;
}