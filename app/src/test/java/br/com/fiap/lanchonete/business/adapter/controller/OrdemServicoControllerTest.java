package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.application.infrastructure.TestContainersInitializer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestContainersInitializer.class)
class OrdemServicoControllerTest {

    @Autowired
    private OrdemServicoController controller;

    @Test
    void findByPedidoIdStatus() {
        OrdemServico os1 = new OrdemServico();
        os1.setNome("Hamburguer");
        os1.setPedidoId(1L);
        os1.setPedidoItemId(1L);
        os1.setProdutoId(1L);
        os1.setQuantidade(1);
        os1.setStatus(OrdemServicoStatus.PRODUCAO);
        os1.setTempoPreparo(3);

        OrdemServico os2 = new OrdemServico();
        os2.setNome("Batata Frita");
        os2.setPedidoId(1L);
        os2.setPedidoItemId(2L);
        os2.setProdutoId(2L);
        os2.setQuantidade(1);
        os2.setStatus(OrdemServicoStatus.PRODUCAO);
        os2.setTempoPreparo(3);

        os1 = controller.save(os1);
        os2 = controller.save(os2);

        List<OrdemServico> oss = controller.findByPedidoIdStatus(1L, OrdemServicoStatus.FINALIZADO);
        Assert.assertEquals(
                "O numero de elementos da lista nao eh o esperado.",
                2,
                oss.size());

        controller.updateStatus(os1.getId(), OrdemServicoStatus.FINALIZADO);

        oss = controller.findByPedidoIdStatus(1L, OrdemServicoStatus.FINALIZADO);
        Assert.assertEquals(
                "O numero de elementos da lista nao eh o esperado.",
                1,
                oss.size());

        controller.updateStatus(os2.getId(), OrdemServicoStatus.FINALIZADO);

        oss = controller.findByPedidoIdStatus(1L, OrdemServicoStatus.FINALIZADO);
        Assert.assertEquals(
                "O numero de elementos da lista nao eh o esperado.",
                0,
                oss.size());
    }
}