package br.com.fiap.lanchonete.application.device.rest.impl;

import br.com.fiap.lanchonete.application.infrastructure.TestContainersInitializer;
import br.com.fiap.lanchonete.business.common.dto.ClienteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.SecureRandom;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestContainersInitializer.class)
class ClienteApiImplTest {

    @Autowired
    private MockMvc mvc;

    private final SecureRandom numberGenerator = new SecureRandom();

    @Test
    void create() throws Exception {
        ClienteDto dto = new ClienteDto();
        dto.setNome("Jose Silva " + Instant.now().getEpochSecond());
        dto.setCpf("05806153088");
        dto.setEmail(numberGenerator.nextLong() + "@x.com");
        dto.setCelular("11-99999-9999");

        mvc.perform(MockMvcRequestBuilders
                        .post("/cliente-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByCpf/" + dto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(dto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(dto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(dto.getCelular()));
    }

    @Test
    void update() throws Exception {
        ClienteDto localDto = new ClienteDto();
        localDto.setNome("Jose Silva " + Instant.now().getEpochSecond());
        localDto.setCpf("39724667570");
        localDto.setEmail(numberGenerator.nextLong() + "@x.com");
        localDto.setCelular("11-99999-9999");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/cliente-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByCpf/" + localDto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(localDto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(localDto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(localDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(localDto.getCelular()));

        localDto.setNome(localDto.getNome() + "-NOVO!");

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .put("/cliente-service/v1/save/" + id)
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(localDto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(localDto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(localDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(localDto.getCelular()));
    }

    @Test
    void findByCpf() throws Exception {
        ClienteDto localDto = new ClienteDto();
        localDto.setNome("Jose Silva " + Instant.now().getEpochSecond());
        localDto.setCpf("79585917483");
        localDto.setEmail(numberGenerator.nextLong() + "@x.com");
        localDto.setCelular("11-99999-9999");

        mvc.perform(MockMvcRequestBuilders
                        .post("/cliente-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByEmail/" + localDto.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(localDto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(localDto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(localDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(localDto.getCelular()));
    }

    @Test
    void deleteByEmail() throws Exception {
        ClienteDto localDto = new ClienteDto();
        localDto.setNome("Jose Silva " + Instant.now().getEpochSecond());
        localDto.setCpf("71575212960");
        localDto.setEmail(numberGenerator.nextLong() + "@x.com");
        localDto.setCelular("11-99999-9999");

        mvc.perform(MockMvcRequestBuilders
                        .post("/cliente-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByCpf/" + localDto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(localDto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(localDto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(localDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(localDto.getCelular()));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/cliente-service/v1/deleteByEmail/" + localDto.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByEmail/" + localDto.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteByCpf() throws Exception {
        ClienteDto localDto = new ClienteDto();
        localDto.setNome("Jose Silva " + Instant.now().getEpochSecond());
        localDto.setCpf("98066327021");
        localDto.setEmail(numberGenerator.nextLong() + "@x.com");
        localDto.setCelular("11-99999-9999");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/cliente-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(localDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByCpf/" + localDto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(localDto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(localDto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(localDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celular").value(localDto.getCelular()));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/cliente-service/v1/deleteByCpf/" + localDto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders
                        .get("/cliente-service/v1/findByCpf/" + localDto.getCpf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}