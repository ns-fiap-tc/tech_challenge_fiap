package br.com.fiap.lanchonete.categoria.device.rest.impl;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.categoria.infrastructure.TestContainersInitializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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
public class CategoriaApiImplTest {

    @Autowired
    private MockMvc mvc;

    private CategoriaDto dto;

    @BeforeEach
    public void init() {
        Date now = new Date();
        String catNome = "Categoria " + now.getTime();
        dto = new CategoriaDto();
        dto.setNome(catNome);
        dto.setTipo(CategoriaTipoEnum.LANCHE);
    }

    @Test
    public void create() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/categoria-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(dto.getTipo().toString()));
    }

    @Test
    public void update() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/categoria-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(dto.getTipo().toString()));

        dto.setNome(dto.getNome() + "-NOVO!");

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .put("/categoria-service/v1/save/" + id)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(dto.getTipo().toString()));
    }

    @Test
    public void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findById/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Bebida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value("BEBIDA"));
    }

    @Test
    public void findByNome() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findByNome/Lanche")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Lanche"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipo").value("LANCHE"));
    }

    @Test
    public void delete() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/categoria-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value(dto.getTipo().toString()));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/categoria-service/v1/deleteById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        result = mvc.perform(MockMvcRequestBuilders
                        .get("/categoria-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertTrue(
                "Null or empty",
                result.getResponse().getContentAsString() == null || result.getResponse().getContentAsString().isEmpty());
    }
}