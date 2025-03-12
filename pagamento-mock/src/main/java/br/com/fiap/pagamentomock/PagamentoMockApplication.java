package br.com.fiap.pagamentomock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PagamentoMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentoMockApplication.class, args);
	}

}