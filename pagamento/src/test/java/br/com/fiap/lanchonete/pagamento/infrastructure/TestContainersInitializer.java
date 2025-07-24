package br.com.fiap.lanchonete.pagamento.infrastructure;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.MongoDBContainer;

/**
 * Classe responsavel pela criacao do container do PostgreSQL a ser utilizado nos testes.
 *
 * Requisito:  estar com o servico do docker em execucao localmente para que seja criado o container do banco de dados
 * durante a execucao da classe de testes.
 */
public class TestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, AfterAllCallback {

    public static final String DB_NAME = "testdb";

    private MongoDBContainer mongoDBContainer;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer
                .withEnv("MONGO_INITDB_DATABASE", DB_NAME);

        mongoDBContainer.start();

        HashMap<String, Object> map = new HashMap<>();
        map.put("spring.data.mongodb.host", mongoDBContainer.getHost());
        map.put("spring.data.mongodb.port", mongoDBContainer.getFirstMappedPort());
        map.put("spring.data.mongodb.database", DB_NAME);

        ConfigurableEnvironment env = applicationContext.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource(
                "testcontainers",
                (Map<String, Object>) map
        ));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (mongoDBContainer == null) {
            return;
        }
        mongoDBContainer.close();
    }
}