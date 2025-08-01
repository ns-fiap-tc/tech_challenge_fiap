package br.com.fiap.lanchonete.application.infrastructure;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

/**
 * Classe responsavel pela criacao do container do PostgreSQL a ser utilizado nos testes.
 *
 * Requisito:  estar com o servico do docker em execucao localmente para que seja criado o container do banco de dados
 * durante a execucao da classe de testes.
 */
public class TestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, AfterAllCallback {

    public static final String DB_NAME = "testdb";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "postgres";

    private PostgreSQLContainer postgreSQLContainer;
    private RabbitMQContainer rabbitMQContainer;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        postgreSQLContainer = new PostgreSQLContainer("postgres:16.5-alpine3.20");
        postgreSQLContainer
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USER)
                .withPassword(DB_PASS);
        postgreSQLContainer.start();

        rabbitMQContainer = new RabbitMQContainer("rabbitmq:4.0.5-management-alpine");
        rabbitMQContainer.start();

        HashMap<String, Object> map = new HashMap<>();
        //db
        map.put("spring.datasource.driver-class-name", postgreSQLContainer.getDriverClassName());
        map.put("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        map.put("spring.datasource.username", postgreSQLContainer.getUsername());
        map.put("spring.datasource.password",postgreSQLContainer.getPassword());
        map.put("spring.liquibase.parameters.datasource.db_username", postgreSQLContainer.getUsername());

        //mq
        map.put("spring.rabbitmq.host", rabbitMQContainer.getHost());
        map.put("spring.rabbitmq.port", rabbitMQContainer.getMappedPort(5672));

        //JWT
        map.put("JWT_KEY_VALUE", "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4");

        ConfigurableEnvironment env = applicationContext.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource(
                "testcontainers",
                (Map<String, Object>) map
        ));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (postgreSQLContainer != null) {
            postgreSQLContainer.close();
        }

        if (rabbitMQContainer != null) {
            rabbitMQContainer.close();
        }
    }
}