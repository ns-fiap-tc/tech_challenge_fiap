package br.com.fiap.lanchonete.infrastructure.config;

import br.com.fiap.lanchonete.business.adapter.controller.CategoriaServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.ClienteController;
import br.com.fiap.lanchonete.business.adapter.controller.OrdemServicoController;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.PedidoController;
import br.com.fiap.lanchonete.business.adapter.controller.ProdutoServiceClient;
import br.com.fiap.lanchonete.business.common.persistence.ClienteRepository;
import br.com.fiap.lanchonete.business.common.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
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

    @Bean
    public ClienteController clienteController(ClienteRepository clienteRepository) {
        return new ClienteController(clienteRepository);
    }

    @Bean
    public OrdemServicoController ordemServicoController(OrdemServicoRepository ordemServicoRepository) {
        return new OrdemServicoController(ordemServicoRepository);
    }

    @Bean
    public PedidoController pedidoController(
            PedidoRepository pedidoRepository,
            MessageProducer messageProducer,
            PagamentoServiceClient pagamentoServiceClient,
            CategoriaServiceClient categoriaServiceClient,
            ProdutoServiceClient produtoServiceClient,
            OrdemServicoController ordemServicoController)
    {
        return new PedidoController(
                pedidoRepository,
                messageProducer,
                pagamentoServiceClient,
                categoriaServiceClient,
                produtoServiceClient,
                ordemServicoController
        );
    }
}