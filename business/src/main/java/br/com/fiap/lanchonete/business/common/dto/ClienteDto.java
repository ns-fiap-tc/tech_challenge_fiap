package br.com.fiap.lanchonete.business.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDto {
    @Null
    private Long id;
    @NotNull
    private String nome;
    @NotNull @Size(min = 11,max = 11) @Pattern(regexp = "[0-9]+")
    private String cpf;
    @NotNull @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotNull
    private String celular;
    @Null
    private Date createdAt;
    @Null
    private Date updatedAt;
}