package br.com.fiap.lanchonete.pagamento.device.rest.impl;

import br.com.fiap.lanchonete.pagamento.commons.domain.FormaPagamento;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pagamento.device.rest.client.PagamentoMockServiceClientImpl;
import br.com.fiap.lanchonete.pagamento.device.rest.client.PedidoServiceClientImpl;
import br.com.fiap.lanchonete.pagamento.infrastructure.TestContainersInitializer;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestContainersInitializer.class)
public class PagamentoApiImplTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PagamentoMockServiceClientImpl pagamentoMockServiceClient;

    @MockitoBean
    private PedidoServiceClientImpl pedidoServiceClient;

    private final ObjectMapper mapper = new ObjectMapper();

    private final Random numberGenerator = new Random();

    private PagamentoDto dto;

    @BeforeEach
    public void init() {
        Date now = new Date();
        dto = new PagamentoDto();
        dto.setForma(FormaPagamento.PIC_PAY);
        dto.setPedidoId(numberGenerator.nextLong());
        dto.setStatus(PagamentoStatus.PENDENTE);
        dto.setValor(BigDecimal.valueOf(100.0));
        dto.setCreatedAt(now);

        // supports Java 8 date time
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void save() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void update() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);
        newDto.setStatus(PagamentoStatus.RECUSADO);
        newDto.setUpdatedAt(new Date());

        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(newDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void findById() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findById/" + newDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void findByPedidoId() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findByPedidoId/" + newDto.getPedidoId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void updateStatusWebhook_falha() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);

        //atualiza com retorno de falha
        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/updateStatus/" + newDto.getPedidoId() + "/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findByPedidoId/" + newDto.getPedidoId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(PagamentoStatus.RECUSADO.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void updateStatusWebhook_sucesso() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);

        mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/updateStatus/" + newDto.getPedidoId() + "/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findByPedidoId/" + newDto.getPedidoId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(PagamentoStatus.CONFIRMADO.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(1))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(0))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

    @Test
    public void realizarPagamento() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pagamento-service/v1/realizarPagamento")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(dto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(dto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(dto.getValor().toString()))
                .andReturn();

        PagamentoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PagamentoDto.class);

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findByPedidoId/" + newDto.getPedidoId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        mvc.perform(MockMvcRequestBuilders
                        .get("/pagamento-service/v1/findByPedidoId/" + newDto.getPedidoId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forma").value(newDto.getForma().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedidoId").value(newDto.getPedidoId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(newDto.getValor().toString()));

        Mockito.verify(pedidoServiceClient, Mockito.times(0))
                .updateStatus(Mockito.anyLong(), (PedidoStatus) Mockito.any());
        Mockito.verify(pagamentoMockServiceClient, Mockito.times(1))
                .realizarPagamento(Mockito.anyLong(), (BigDecimal) Mockito.any());
    }

}