package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.device.rest.client.PagamentoServiceClientImpl;
import br.com.fiap.lanchonete.application.infrastructure.TestContainersInitializer;
import br.com.fiap.lanchonete.pagamento.commons.domain.FormaPagamento;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoItemDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
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
class PedidoApiImplTest {

    @Value("${jwt.key.value}")
    private String secret;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PagamentoServiceClientImpl pagamentoServiceClient;

    private final ObjectMapper mapper = new ObjectMapper();

    private PedidoDto dto;

    @BeforeEach
    void init() {
        Date now = new Date();

        PagamentoDto pagamentoDto = new PagamentoDto();
        pagamentoDto.setForma(FormaPagamento.PIC_PAY);
        pagamentoDto.setStatus(PagamentoStatus.PENDENTE);
        pagamentoDto.setValor(BigDecimal.valueOf(100.0));
        pagamentoDto.setCreatedAt(now);

        PedidoItemDto itemDto = new PedidoItemDto();
        itemDto.setNome("Hamburguer");
        itemDto.setObservacoes("Carne ao ponto");
        itemDto.setQuantidade(2);
        itemDto.setValorUnitario(50D);
        itemDto.setProdutoId(1L);

        dto = new PedidoDto();
        dto.setClienteId(-1L);
        dto.setPagamentoDto(pagamentoDto);
        dto.setTempoEspera(46L);
        dto.setStatus(PedidoStatus.CRIACAO);
        dto.setItens(List.of(itemDto));

        // supports Java 8 date time
        mapper.registerModule(new JavaTimeModule());

        SecurityContextHolder.clearContext();
    }

    private String tokenGenerator(String cpf) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("LanchoneteApp")
                    .withClaim("cfp", cpf)
                    //.withClaim("id", usuarios.getId())
                    //.withClaim("username", usuarios.getUsername())
                    //.withClaim("rolesUsuarios", String.valueOf(usuarios.getRolesUsuarios()))
                    //.withExpiresAt(dataExpiracao())
                    //.withExpiresAt(expirationData())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Erro ao gerar o token", exception);
        }
    }

    @Test
    void create() throws Exception {
        PagamentoDto savedPagtoDto = new PagamentoDto();
        BeanUtils.copyProperties(dto.getPagamentoDto(), savedPagtoDto);
        savedPagtoDto.setId(UUID.randomUUID().toString());

        Mockito.when(this.pagamentoServiceClient.save((PagamentoDto) Mockito.any()))
                        .thenReturn(savedPagtoDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/pedido-service/v1/save")
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(dto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(dto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(dto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(dto.getItens().get(0).getValorUnitario().toString()));

        Mockito.verify(pagamentoServiceClient, Mockito.times(1))
                .save((PagamentoDto) Mockito.any());
        Mockito.verify(pagamentoServiceClient, Mockito.times(0))
                .realizarPagamento((PagamentoDto) Mockito.any());
    }

    @Test
    void recebido() throws Exception {
        PedidoDto localDto = new PedidoDto();
        BeanUtils.copyProperties(dto, localDto);
        localDto.setStatus(PedidoStatus.RECEBIDO);

        PagamentoDto savedPagtoDto = new PagamentoDto();
        BeanUtils.copyProperties(dto.getPagamentoDto(), savedPagtoDto);
        savedPagtoDto.setId(UUID.randomUUID().toString());

        Mockito.when(this.pagamentoServiceClient.realizarPagamento((PagamentoDto) Mockito.any()))
                .thenReturn(savedPagtoDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/pedido-service/v1/save")
                        .header("Authorization", "Bearer " + this.tokenGenerator("23454367897"))
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(localDto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(localDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(localDto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(localDto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(localDto.getItens().get(0).getValorUnitario().toString()));

        Mockito.verify(pagamentoServiceClient, Mockito.times(0))
                .save((PagamentoDto) Mockito.any());
        Mockito.verify(pagamentoServiceClient, Mockito.times(1))
                .realizarPagamento((PagamentoDto) Mockito.any());
    }

    @Test
    void atualizado_finalizado() throws Exception {
        PagamentoDto savedPagtoDto = new PagamentoDto();
        BeanUtils.copyProperties(dto.getPagamentoDto(), savedPagtoDto);
        savedPagtoDto.setId(UUID.randomUUID().toString());

        Mockito.when(this.pagamentoServiceClient.save((PagamentoDto) Mockito.any()))
                .thenReturn(savedPagtoDto);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pedido-service/v1/save")
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(dto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(dto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(dto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(dto.getItens().get(0).getValorUnitario().toString()))
                .andReturn();

        PedidoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PedidoDto.class);

        Mockito.verify(pagamentoServiceClient, Mockito.times(1))
                .save((PagamentoDto) Mockito.any());
        Mockito.verify(pagamentoServiceClient, Mockito.times(0))
                .realizarPagamento((PagamentoDto) Mockito.any());


        newDto.setStatus(PedidoStatus.RECEBIDO);
        newDto.setPagamentoDto(savedPagtoDto);
        newDto.setCreatedAt(null);
        newDto.setUpdatedAt(null);

        Long pedidoId = newDto.getId();
        newDto.setId(null);

        Mockito.when(this.pagamentoServiceClient.realizarPagamento((PagamentoDto) Mockito.any()))
                .thenReturn(savedPagtoDto);

        mvc.perform(MockMvcRequestBuilders
                        .put("/pedido-service/v1/save/" + pedidoId)
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .content(new ObjectMapper().writeValueAsString(newDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(newDto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newDto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(newDto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(newDto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(newDto.getItens().get(0).getValorUnitario().toString()));

        Mockito.verify(pagamentoServiceClient, Mockito.times(1))
                .save((PagamentoDto) Mockito.any());
        Mockito.verify(pagamentoServiceClient, Mockito.times(1))
                .realizarPagamento((PagamentoDto) Mockito.any());
    }

    @Test
    void updateStatus() throws Exception {
        PedidoDto localDto = new PedidoDto();
        BeanUtils.copyProperties(dto, localDto);
        localDto.setStatus(PedidoStatus.RECEBIDO);

        PagamentoDto savedPagtoDto = new PagamentoDto();
        BeanUtils.copyProperties(dto.getPagamentoDto(), savedPagtoDto);
        savedPagtoDto.setId(UUID.randomUUID().toString());

        Mockito.when(this.pagamentoServiceClient.save((PagamentoDto) Mockito.any()))
                .thenReturn(savedPagtoDto);
        Mockito.when(this.pagamentoServiceClient.realizarPagamento((PagamentoDto) Mockito.any()))
                .thenReturn(savedPagtoDto);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/pedido-service/v1/save")
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(dto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(dto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(dto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(dto.getItens().get(0).getValorUnitario().toString()))
                .andReturn();

        PedidoDto newDto = mapper.readValue(result.getResponse().getContentAsString(), PedidoDto.class);

        mvc.perform(MockMvcRequestBuilders
                        .put("/pedido-service/v1/updateStatus/" + newDto.getId())
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .content(new ObjectMapper().writeValueAsString(PedidoStatus.PREPARACAO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));


        mvc.perform(MockMvcRequestBuilders
                        .get("/pedido-service/v1/findById/" + newDto.getId())
                        .header("Authorization", "Bearer " + this.tokenGenerator(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clienteId").value(newDto.getClienteId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoDto").value(Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pagamentoId").value(savedPagtoDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(PedidoStatus.PREPARACAO.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].nome").value(newDto.getItens().get(0).getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].produtoId").value(newDto.getItens().get(0).getProdutoId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itens[0].valorUnitario").value(newDto.getItens().get(0).getValorUnitario().toString()))
                .andReturn();
    }
}