package br.com.fiap.lanchonete.application.steps;

import br.com.fiap.lanchonete.business.adapter.controller.OrdemServicoController;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdemServicoSteps extends StepDefsDefault {
    @Autowired
    private OrdemServicoController controller;
    private List<OrdemServico> ordemServicosResult;

    @Given("^I create an OrdemServico and store to database.$")
    public void createOrdemServico(DataTable dataTable) {
        List<Map<String, Object>> oss = dataTable.asMaps(String.class, Object.class);
        for (Map<String, Object> osData : oss) {
            OrdemServico os = new OrdemServico();
            os.setNome((String) osData.get("nome"));
            os.setPedidoId(Long.valueOf((String) osData.get("pedidoId")));
            os.setPedidoItemId(Long.valueOf((String) osData.get("pedidoItemId")));
            os.setProdutoId(Long.valueOf((String) osData.get("produtoId")));
            os.setQuantidade(Integer.parseInt((String) osData.get("quantidade")));
            os.setStatus(OrdemServicoStatus.valueOf((String) osData.get("status")));
            os.setTempoPreparo(Integer.parseInt((String) osData.get("tempoPreparo")));
            controller.save(os);
        }
    }

    @When("I search by the pedidoId {int}.")
    public void iSearchByThePedidoId(int pedidoId) {
        ordemServicosResult = controller.findByPedidoId((long) pedidoId);
    }

    @Then("I should find {int} object\\(s).")
    public void iShouldFindObjectS(int size) {
        Assertions.assertEquals(size, ordemServicosResult.size(), "O numero de elementos encontrados nao eh o esperado.");
    }

    @When("I search by pedidoId {int} and status {string}.")
    public void iSearchByPedidoIdAndStatus(int pedidoId, String status) {
        ordemServicosResult = controller.findByPedidoIdStatus((long) pedidoId, OrdemServicoStatus.valueOf((String) status));
    }
}