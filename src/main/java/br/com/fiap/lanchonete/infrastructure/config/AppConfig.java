package br.com.fiap.lanchonete.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.description}")
    private String appDescription;

    @Value("${app.version}")
    private String version;

    @Value("${app.build.timestamp}")
    private String buildTimestamp;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(this.appName + " API")
                        .description(this.appName + " v" + version
                                + " build: " + buildTimestamp
                                + " (" + this.profile + ")"
                                + " - API reference for developers.")
                        .version(version)
                );
    }
}
