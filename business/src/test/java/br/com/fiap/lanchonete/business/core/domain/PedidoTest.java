package br.com.fiap.lanchonete.business.core.domain;

import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void shouldCreatePedidoWithAllArgsConstructor() {
        Date now = new Date();
        List<PedidoItem> itens = Arrays.asList(new PedidoItem(1L, "Item1", 100L, 1, 10.0, null));
        
        Pedido pedido = new Pedido(1L, 100L, PedidoStatus.RECEBIDO, itens, "PAG123", now, now);
        
        assertEquals(1L, pedido.getId());
        assertEquals(100L, pedido.getClienteId());
        assertEquals(PedidoStatus.RECEBIDO, pedido.getStatus());
        assertEquals(itens, pedido.getItens());
        assertEquals("PAG123", pedido.getPagamentoId());
        assertEquals(now, pedido.getCreatedAt());
        assertEquals(now, pedido.getUpdatedAt());
    }

    @Test
    void shouldCreatePedidoWithNoArgsConstructor() {
        Pedido pedido = new Pedido();
        
        assertNull(pedido.getId());
        assertNull(pedido.getClienteId());
        assertNull(pedido.getStatus());
        assertNull(pedido.getItens());
        assertNull(pedido.getPagamentoId());
        assertNull(pedido.getCreatedAt());
        assertNull(pedido.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetProperties() {
        Pedido pedido = new Pedido();
        Date now = new Date();
        List<PedidoItem> itens = Arrays.asList(new PedidoItem(1L, "Item1", 100L, 1, 10.0, null));
        
        pedido.setId(2L);
        pedido.setClienteId(200L);
        pedido.setStatus(PedidoStatus.RECEBIDO);
        pedido.setItens(itens);
        pedido.setPagamentoId("PAG456");
        pedido.setCreatedAt(now);
        pedido.setUpdatedAt(now);
        
        assertEquals(2L, pedido.getId());
        assertEquals(200L, pedido.getClienteId());
        assertEquals(PedidoStatus.RECEBIDO, pedido.getStatus());
        assertEquals(itens, pedido.getItens());
        assertEquals("PAG456", pedido.getPagamentoId());
        assertEquals(now, pedido.getCreatedAt());
        assertEquals(now, pedido.getUpdatedAt());
    }

    @Test
    void shouldCalculateTempoEsperaForPendingOrder() {
        Pedido pedido = new Pedido();
        pedido.setStatus(PedidoStatus.PREPARACAO);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -30);
        pedido.setCreatedAt(cal.getTime());
        
        long tempoEspera = pedido.getTempoEspera();
        assertTrue(tempoEspera >= 29 && tempoEspera <= 31);
    }

    @Test
    void shouldReturnZeroTempoEsperaForFinishedOrder() {
        Pedido pedido = new Pedido();
        pedido.setStatus(PedidoStatus.PRONTO);
        pedido.setCreatedAt(new Date());
        
        assertEquals(0L, pedido.getTempoEspera());
    }

    @Test
    void shouldReturnZeroTempoEsperaForFinalizedOrder() {
        Pedido pedido = new Pedido();
        pedido.setStatus(PedidoStatus.FINALIZADO);
        pedido.setCreatedAt(new Date());
        
        assertEquals(0L, pedido.getTempoEspera());
    }

    @Test
    void shouldCalculateValorTotalWithItems() {
        PedidoItem item1 = new PedidoItem(1L, "Item1", 100L, 2, 10.0, null);
        PedidoItem item2 = new PedidoItem(2L, "Item2", 200L, 1, 15.0, null);
        List<PedidoItem> itens = Arrays.asList(item1, item2);
        
        Pedido pedido = new Pedido();
        pedido.setItens(itens);
        
        assertEquals(35.0, pedido.getValorTotal());
    }

    @Test
    void shouldReturnZeroValorTotalWithNullItems() {
        Pedido pedido = new Pedido();
        pedido.setItens(null);
        
        assertEquals(0.0, pedido.getValorTotal());
    }

    @Test
    void shouldReturnZeroValorTotalWithEmptyItems() {
        Pedido pedido = new Pedido();
        pedido.setItens(Arrays.asList());
        
        assertEquals(0.0, pedido.getValorTotal());
    }

    @Test
    void shouldHaveCorrectToStringRepresentation() {
        Pedido pedido = new Pedido(1L, 100L, PedidoStatus.RECEBIDO, null, "PAG123", new Date(), new Date());
        
        String toString = pedido.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("clienteId=100"));
        assertTrue(toString.contains("status=RECEBIDO"));
        assertTrue(toString.contains("pagamentoId=PAG123"));
    }
}