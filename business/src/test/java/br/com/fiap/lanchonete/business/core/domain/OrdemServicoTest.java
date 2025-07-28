package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrdemServicoTest {

    @Test
    void shouldCreateOrdemServicoWithAllArgsConstructor() {
        Date now = new Date();
        OrdemServico ordem = new OrdemServico(1L, 100L, 200L, "Hambúrguer", 300L, 
                OrdemServicoStatus.AGUARDANDO, 600, 2, now, now);
        
        assertEquals(1L, ordem.getId());
        assertEquals(100L, ordem.getPedidoId());
        assertEquals(200L, ordem.getPedidoItemId());
        assertEquals("Hambúrguer", ordem.getNome());
        assertEquals(300L, ordem.getProdutoId());
        assertEquals(OrdemServicoStatus.AGUARDANDO, ordem.getStatus());
        assertEquals(600, ordem.getTempoPreparo());
        assertEquals(2, ordem.getQuantidade());
        assertEquals(now, ordem.getCreatedAt());
        assertEquals(now, ordem.getUpdatedAt());
    }

    @Test
    void shouldCreateOrdemServicoWithNoArgsConstructor() {
        OrdemServico ordem = new OrdemServico();
        
        assertNull(ordem.getId());
        assertNull(ordem.getPedidoId());
        assertNull(ordem.getPedidoItemId());
        assertNull(ordem.getNome());
        assertNull(ordem.getProdutoId());
        assertNull(ordem.getStatus());
        assertEquals(0, ordem.getTempoPreparo());
        assertEquals(0, ordem.getQuantidade());
        assertNull(ordem.getCreatedAt());
        assertNull(ordem.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetProperties() {
        OrdemServico ordem = new OrdemServico();
        Date now = new Date();
        
        ordem.setId(5L);
        ordem.setPedidoId(150L);
        ordem.setPedidoItemId(250L);
        ordem.setNome("Pizza");
        ordem.setProdutoId(350L);
        ordem.setStatus(OrdemServicoStatus.PRODUCAO);
        ordem.setTempoPreparo(900);
        ordem.setQuantidade(1);
        ordem.setCreatedAt(now);
        ordem.setUpdatedAt(now);
        
        assertEquals(5L, ordem.getId());
        assertEquals(150L, ordem.getPedidoId());
        assertEquals(250L, ordem.getPedidoItemId());
        assertEquals("Pizza", ordem.getNome());
        assertEquals(350L, ordem.getProdutoId());
        assertEquals(OrdemServicoStatus.PRODUCAO, ordem.getStatus());
        assertEquals(900, ordem.getTempoPreparo());
        assertEquals(1, ordem.getQuantidade());
        assertEquals(now, ordem.getCreatedAt());
        assertEquals(now, ordem.getUpdatedAt());
    }

    @Test
    void shouldHaveCorrectToStringRepresentation() {
        OrdemServico ordem = new OrdemServico(1L, 100L, 200L, "Hambúrguer", 300L, 
                OrdemServicoStatus.FINALIZADO, 600, 2, new Date(), new Date());
        
        String toString = ordem.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("pedidoId=100"));
        assertTrue(toString.contains("nome=Hambúrguer"));
        assertTrue(toString.contains("status=FINALIZADO"));
    }
}