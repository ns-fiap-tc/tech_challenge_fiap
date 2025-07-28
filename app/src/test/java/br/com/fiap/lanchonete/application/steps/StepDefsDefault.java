package br.com.fiap.lanchonete.application.steps;

import br.com.fiap.lanchonete.application.infrastructure.TestContainersInitializer;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/*
 * Cria o contexto para os testes, via Cucumber.
 */
@CucumberContextConfiguration
@SpringBootTest
@ContextConfiguration(initializers = TestContainersInitializer.class)
public class StepDefsDefault {
}