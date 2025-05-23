package br.com.fiap.lanchonete.infrastructure.config;

import br.com.fiap.lanchonete.business.adapter.controller.CategoriaController;
import br.com.fiap.lanchonete.business.adapter.controller.ClienteController;
import br.com.fiap.lanchonete.business.adapter.controller.OrdemServicoController;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoController;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.PedidoController;
import br.com.fiap.lanchonete.business.adapter.controller.ProdutoController;
import br.com.fiap.lanchonete.business.common.persistence.CategoriaRepository;
import br.com.fiap.lanchonete.business.common.persistence.ClienteRepository;
import br.com.fiap.lanchonete.business.common.persistence.OrdemServicoRepository;
import br.com.fiap.lanchonete.business.common.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.common.persistence.ProdutoRepository;
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
    public CategoriaController categoriaController(CategoriaRepository categoriaRepository) {
        return new CategoriaController(categoriaRepository);
    }

    @Bean
    public OrdemServicoController ordemServicoController(OrdemServicoRepository ordemServicoRepository) {
        return new OrdemServicoController(ordemServicoRepository);
    }

    @Bean
    public PagamentoController pagamentoController(PagamentoRepository pagamentoRepository) {
        return new PagamentoController(pagamentoRepository);
    }

    @Bean
    public PedidoController pedidoController(
            PedidoRepository pedidoRepository,
            MessageProducer messageProducer,
            PagamentoServiceClient pagamentoServiceClient,
            PagamentoController pagamentoController,
            CategoriaController categoriaController,
            ProdutoController produtoController,
            OrdemServicoController ordemServicoController)
    {
        return new PedidoController(
                pedidoRepository,
                messageProducer,
                pagamentoServiceClient,
                pagamentoController,
                categoriaController,
                produtoController,
                ordemServicoController
        );
    }

    @Bean
    public ProdutoController produtoController(ProdutoRepository produtoRepository) {
        return new ProdutoController(produtoRepository);
    }
}