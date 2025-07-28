package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void shouldCreateClienteWithAllArgsConstructor() {
        Date now = new Date();
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "joao@email.com", "11999999999", now, now);
        
        assertEquals(1L, cliente.getId());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("11999999999", cliente.getCelular());
        assertEquals(now, cliente.getCreatedAt());
        assertEquals(now, cliente.getUpdatedAt());
    }

    @Test
    void shouldCreateClienteWithNoArgsConstructor() {
        Cliente cliente = new Cliente();
        
        assertNull(cliente.getId());
        assertNull(cliente.getNome());
        assertNull(cliente.getCpf());
        assertNull(cliente.getEmail());
        assertNull(cliente.getCelular());
        assertNull(cliente.getCreatedAt());
        assertNull(cliente.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetProperties() {
        Cliente cliente = new Cliente();
        Date now = new Date();
        
        cliente.setId(2L);
        cliente.setNome("Maria Santos");
        cliente.setCpf("98765432100");
        cliente.setEmail("maria@email.com");
        cliente.setCelular("11888888888");
        cliente.setCreatedAt(now);
        cliente.setUpdatedAt(now);
        
        assertEquals(2L, cliente.getId());
        assertEquals("Maria Santos", cliente.getNome());
        assertEquals("98765432100", cliente.getCpf());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("11888888888", cliente.getCelular());
        assertEquals(now, cliente.getCreatedAt());
        assertEquals(now, cliente.getUpdatedAt());
    }

    @Test
    void shouldHaveCorrectConstantValue() {
        assertEquals(-1L, Cliente.CLIENTE_NAO_IDENTIFICADO);
    }
}