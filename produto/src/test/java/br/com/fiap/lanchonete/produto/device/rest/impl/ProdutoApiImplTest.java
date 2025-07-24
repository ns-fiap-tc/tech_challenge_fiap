package br.com.fiap.lanchonete.produto.device.rest.impl;

import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
import br.com.fiap.lanchonete.produto.infrastructure.TestContainersInitializer;
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
public class ProdutoApiImplTest {

    @Autowired
    private MockMvc mvc;

    private ProdutoDto dto;

    @BeforeEach
    public void init() {
        Date now = new Date();
        String catNome = "Produto " + now.getTime();
        dto = new ProdutoDto();
        dto.setNome(catNome);
        dto.setDescricao("Descricao do " + dto.getNome());
        dto.setPreco(10.0D);
        dto.setCategoriaTipoEnum(CategoriaTipoEnum.LANCHE);
        dto.setTempoPreparo(5);
        dto.setFotoUrl("https://images.unsplash.com/photo-1630431341973-02e1b662ec32");
    }

    @Test
    public void create() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/produto-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaTipoEnum").value(dto.getCategoriaTipoEnum().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempoPreparo").value(dto.getTempoPreparo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(String.valueOf(dto.getPreco())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrl").value(dto.getFotoUrl()));
    }

    @Test
    public void update() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/produto-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaTipoEnum").value(dto.getCategoriaTipoEnum().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempoPreparo").value(dto.getTempoPreparo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(String.valueOf(dto.getPreco())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrl").value(dto.getFotoUrl()));

        dto.setNome(dto.getNome() + "-NOVO!");

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .put("/produto-service/v1/save/" + id)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaTipoEnum").value(dto.getCategoriaTipoEnum().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempoPreparo").value(dto.getTempoPreparo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(String.valueOf(dto.getPreco())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrl").value(dto.getFotoUrl()));
    }

    @Test
    public void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findById/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Batata Frita"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Batatas fritas crocantes por fora e macias por dentro, temperadas com sal e servidas bem quentinhas. Porção generosa ideal para compartilhar."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaTipoEnum").value("ACOMPANHAMENTO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempoPreparo").value(300))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(14.9))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrl").value("https://images.unsplash.com/photo-1630431341973-02e1b662ec35"));
    }

    @Test
    public void findByNome() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findByNome/Cheesebacon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Cheesebacon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Hambúrguer artesanal de 150g, queijo cheddar derretido, bacon crocante, alface, tomate, cebola roxa e molho especial da casa em pão brioche."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaTipoEnum").value("LANCHE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tempoPreparo").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].preco").value(34.9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fotoUrl").value("https://images.unsplash.com/photo-1603508102971-03777763fdf2"));
    }

    @Test
    public void findByCategoria() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findByCategoria/" + CategoriaTipoEnum.LANCHE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Hambúrguer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Hambúrguer artesanal grelhado de 150g, alface crocante, tomate, cebola roxa e molho especial da casa em pão brioche."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaTipoEnum").value("LANCHE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tempoPreparo").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].preco").value(28.9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fotoUrl").value("https://images.unsplash.com/photo-1508736793122-f516e3ba5569"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Cheeseburguer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value("Hambúrguer artesanal de 150g, queijo cheddar derretido, alface, tomate, cebola roxa e molho especial da casa em pão brioche."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].categoriaTipoEnum").value("LANCHE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tempoPreparo").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].preco").value(32.9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fotoUrl").value("https://images.unsplash.com/photo-1619901282828-7cbde1c89884"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nome").value("Cheesebacon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value("Hambúrguer artesanal de 150g, queijo cheddar derretido, bacon crocante, alface, tomate, cebola roxa e molho especial da casa em pão brioche."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].categoriaTipoEnum").value("LANCHE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].tempoPreparo").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].preco").value(34.9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].fotoUrl").value("https://images.unsplash.com/photo-1603508102971-03777763fdf2"));
    }

    @Test
    public void delete() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/produto-service/v1/save")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        //validar a criacao
        mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaTipoEnum").value(dto.getCategoriaTipoEnum().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempoPreparo").value(dto.getTempoPreparo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preco").value(String.valueOf(dto.getPreco())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fotoUrl").value(dto.getFotoUrl()));

        mvc.perform(MockMvcRequestBuilders
                        .delete("/produto-service/v1/deleteById/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        result = mvc.perform(MockMvcRequestBuilders
                        .get("/produto-service/v1/findById/" + id)
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