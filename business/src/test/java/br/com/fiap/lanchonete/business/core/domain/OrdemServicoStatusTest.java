package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdemServicoStatusTest {

    @Test
    void shouldHaveCorrectEnumValues() {
        assertEquals(3, OrdemServicoStatus.values().length);
        assertEquals(OrdemServicoStatus.AGUARDANDO, OrdemServicoStatus.valueOf("AGUARDANDO"));
        assertEquals(OrdemServicoStatus.PRODUCAO, OrdemServicoStatus.valueOf("PRODUCAO"));
        assertEquals(OrdemServicoStatus.FINALIZADO, OrdemServicoStatus.valueOf("FINALIZADO"));
    }

    @Test
    void shouldReturnCorrectOrdinal() {
        assertEquals(0, OrdemServicoStatus.AGUARDANDO.ordinal());
        assertEquals(1, OrdemServicoStatus.PRODUCAO.ordinal());
        assertEquals(2, OrdemServicoStatus.FINALIZADO.ordinal());
    }

    @Test
    void shouldReturnCorrectName() {
        assertEquals("AGUARDANDO", OrdemServicoStatus.AGUARDANDO.name());
        assertEquals("PRODUCAO", OrdemServicoStatus.PRODUCAO.name());
        assertEquals("FINALIZADO", OrdemServicoStatus.FINALIZADO.name());
    }
}