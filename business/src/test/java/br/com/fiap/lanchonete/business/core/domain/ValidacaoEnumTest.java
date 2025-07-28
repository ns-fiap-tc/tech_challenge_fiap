package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoEnumTest {

    @Test
    void shouldHaveCorrectEnumValues() {
        assertEquals(6, ValidacaoEnum.values().length);
        
        assertEquals(ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA, ValidacaoEnum.valueOf("ENTRADA_DE_DADOS_INVALIDA"));
        assertEquals(ValidacaoEnum.CPF_INVALIDO, ValidacaoEnum.valueOf("CPF_INVALIDO"));
        assertEquals(ValidacaoEnum.CPF_JA_CADASTRADO, ValidacaoEnum.valueOf("CPF_JA_CADASTRADO"));
        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO, ValidacaoEnum.valueOf("CLIENTE_NAO_ENCONTRADO"));
        assertEquals(ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO, ValidacaoEnum.valueOf("PAGAMENTO_NAO_CONFIRMADO"));
        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO, ValidacaoEnum.valueOf("NAO_IDENTIFICADO"));
    }

    @Test
    void shouldReturnCorrectCodigoAndDescricao() {
        assertEquals(Integer.valueOf(-1), ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA.getCodigo());
        assertEquals("Existe(m) campo(s) obrigatório(s) não informado(s).", ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA.getDescricao());
        
        assertEquals(Integer.valueOf(-2), ValidacaoEnum.CPF_INVALIDO.getCodigo());
        assertEquals("O cpf informado é inválido.", ValidacaoEnum.CPF_INVALIDO.getDescricao());
        
        assertEquals(Integer.valueOf(-3), ValidacaoEnum.CPF_JA_CADASTRADO.getCodigo());
        assertEquals("O cpf informado já existe.", ValidacaoEnum.CPF_JA_CADASTRADO.getDescricao());
        
        assertEquals(Integer.valueOf(-4), ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getCodigo());
        assertEquals("Não foi possível localizar o cliente.", ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getDescricao());
        
        assertEquals(Integer.valueOf(-5), ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO.getCodigo());
        assertEquals("O pagamento foi recusado.", ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO.getDescricao());
        
        assertEquals(Integer.valueOf(-999), ValidacaoEnum.NAO_IDENTIFICADO.getCodigo());
        assertEquals("Ocorreu um problema não identificado.", ValidacaoEnum.NAO_IDENTIFICADO.getDescricao());
    }

    @Test
    void shouldReturnCorrectEnumFromCodigo() {
        assertEquals(ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA, ValidacaoEnum.valueOf(-1));
        assertEquals(ValidacaoEnum.CPF_INVALIDO, ValidacaoEnum.valueOf(-2));
        assertEquals(ValidacaoEnum.CPF_JA_CADASTRADO, ValidacaoEnum.valueOf(-3));
        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO, ValidacaoEnum.valueOf(-4));
        assertEquals(ValidacaoEnum.PAGAMENTO_NAO_CONFIRMADO, ValidacaoEnum.valueOf(-5));
        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO, ValidacaoEnum.valueOf(-999));
    }

    @Test
    void shouldReturnNullForInvalidCodigo() {
        assertNull(ValidacaoEnum.valueOf(0));
        assertNull(ValidacaoEnum.valueOf(100));
        assertNull(ValidacaoEnum.valueOf(-100));
    }
}