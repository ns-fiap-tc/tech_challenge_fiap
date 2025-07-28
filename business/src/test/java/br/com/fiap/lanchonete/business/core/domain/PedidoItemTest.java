package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoItemTest {

    @Test
    void shouldCreatePedidoItemWithAllArgsConstructor() {
        PedidoItem item = new PedidoItem(1L, "Hambúrguer", 100L, 2, 15.50, "Sem cebola");
        
        assertEquals(1L, item.getId());
        assertEquals("Hambúrguer", item.getNome());
        assertEquals(100L, item.getProdutoId());
        assertEquals(2, item.getQuantidade());
        assertEquals(15.50, item.getValorUnitario());
        assertEquals("Sem cebola", item.getObservacoes());
    }

    @Test
    void shouldCreatePedidoItemWithNoArgsConstructor() {
        PedidoItem item = new PedidoItem();
        
        assertNull(item.getId());
        assertNull(item.getNome());
        assertNull(item.getProdutoId());
        assertEquals(0, item.getQuantidade());
        assertNull(item.getValorUnitario());
        assertNull(item.getObservacoes());
    }

    @Test
    void shouldSetAndGetProperties() {
        PedidoItem item = new PedidoItem();
        
        item.setId(2L);
        item.setNome("Pizza");
        item.setProdutoId(200L);
        item.setQuantidade(1);
        item.setValorUnitario(25.00);
        item.setObservacoes("Massa fina");
        
        assertEquals(2L, item.getId());
        assertEquals("Pizza", item.getNome());
        assertEquals(200L, item.getProdutoId());
        assertEquals(1, item.getQuantidade());
        assertEquals(25.00, item.getValorUnitario());
        assertEquals("Massa fina", item.getObservacoes());
    }

    @Test
    void shouldCalculateValorTotalCorrectly() {
        PedidoItem item = new PedidoItem(1L, "Hambúrguer", 100L, 3, 12.50, null);
        
        assertEquals(37.50, item.getValorTotal());
    }

    @Test
    void shouldCalculateValorTotalWithZeroQuantity() {
        PedidoItem item = new PedidoItem(1L, "Hambúrguer", 100L, 0, 12.50, null);
        
        assertEquals(0.0, item.getValorTotal());
    }

    @Test
    void shouldCalculateValorTotalWithNullValorUnitario() {
        PedidoItem item = new PedidoItem(1L, "Hambúrguer", 100L, 2, null, null);
        
        assertThrows(NullPointerException.class, item::getValorTotal);
    }

    @Test
    void shouldHaveCorrectToStringRepresentation() {
        PedidoItem item = new PedidoItem(1L, "Hambúrguer", 100L, 2, 15.50, "Sem cebola");
        
        String toString = item.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=Hambúrguer"));
        assertTrue(toString.contains("quantidade=2"));
        assertTrue(toString.contains("valorUnitario=15.5"));
    }
}