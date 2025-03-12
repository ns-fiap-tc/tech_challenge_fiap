package br.com.fiap.lanchonete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LanchoneteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanchoneteApplication.class, args);
	}

}