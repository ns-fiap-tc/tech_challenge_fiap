package br.com.fiap.lanchonete.application.device.rest.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Objeto que representa a resposta a ser enviada quando ocorrer um erro dentro do Filter.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:sssXXX")
    private Date timestamp;
    private int status;
    private String error;
    private String path;
}
