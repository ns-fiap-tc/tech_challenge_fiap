package br.com.fiap.pagamentomock.adapter.input.controller;

import br.com.fiap.pagamentomock.adapter.output.rest.client.PagamentoWebhookClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class PagamentoMockApiImplTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PagamentoWebhookClient pagamentoWebhookClient;

    @Test
    void realizarPagamento() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-mock-service/v1/realizarPagamento/1/435.98")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        Mockito.verify(pagamentoWebhookClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    void retornarConfirmacaoPagamentoWebHookCall_sucesso() throws Exception {
        Long pedidoId = 1L;
        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-mock-service/v1/callPagamentoWebHook/" + pedidoId + "/true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(pedidoId + ";100"));

        Mockito.verify(pagamentoWebhookClient, Mockito.times(1))
                .updateStatus(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    void retornarConfirmacaoPagamentoWebHookCall_falha() throws Exception {
        Long pedidoId = 1L;
        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-mock-service/v1/callPagamentoWebHook/" + pedidoId + "/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(pedidoId + ";999"));

        Mockito.verify(pagamentoWebhookClient, Mockito.times(1))
                .updateStatus(Mockito.anyLong(), Mockito.anyString());
    }
}