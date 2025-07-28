package br.com.fiap.lanchonete.business.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfUtilsTest {

    @Test
    void shouldReturnTrueForValidCpf() {
        assertTrue(CpfUtils.isValid("11144477735"));
        assertTrue(CpfUtils.isValid("12345678909"));
    }

    @Test
    void shouldReturnFalseForNullCpf() {
        assertFalse(CpfUtils.isValid(null));
    }

    @Test
    void shouldReturnFalseForCpfWithIncorrectLength() {
        assertFalse(CpfUtils.isValid("123456789"));
        assertFalse(CpfUtils.isValid("123456789012"));
        assertFalse(CpfUtils.isValid(""));
    }

    @Test
    void shouldReturnFalseForCpfWithAllSameDigits() {
        assertFalse(CpfUtils.isValid("11111111111"));
        assertFalse(CpfUtils.isValid("22222222222"));
        assertFalse(CpfUtils.isValid("00000000000"));
    }

    @Test
    void shouldReturnFalseForInvalidCpf() {
        assertFalse(CpfUtils.isValid("12345678901"));
        assertFalse(CpfUtils.isValid("11144477736"));
    }
}