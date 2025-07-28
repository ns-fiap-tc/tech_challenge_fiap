package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.ClienteGateway;
import br.com.fiap.lanchonete.business.core.domain.Cliente;
import br.com.fiap.lanchonete.business.core.domain.ValidacaoEnum;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoException;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoNotFoundException;
import br.com.fiap.lanchonete.business.core.exception.ValidacaoRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteUseCasesImplTest {

    @Mock
    private ClienteGateway gateway;

    @InjectMocks
    private ClienteUseCasesImpl clienteUseCases;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("83428779061");
        cliente.setEmail("joao@email.com");
    }

    @Test
    void save_ValidCliente_Success() {
        when(gateway.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteUseCases.save(cliente);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        verify(gateway).save(cliente);
    }

    @Test
    void save_InvalidCpf_ThrowsValidacaoException() {
        cliente.setCpf("123");

        ValidacaoException exception = assertThrows(ValidacaoException.class, 
            () -> clienteUseCases.save(cliente));

        assertEquals(ValidacaoEnum.CPF_INVALIDO.getCodigo(), exception.getCodigo());
        verify(gateway, never()).save(any());
    }

    @Test
    void save_CpfAlreadyExists_ThrowsValidacaoException() {
        cliente.setId(null);
        when(gateway.findByCpf(anyString())).thenReturn(new Cliente());

        ValidacaoException exception = assertThrows(ValidacaoException.class, 
            () -> clienteUseCases.save(cliente));

        assertEquals(ValidacaoEnum.CPF_JA_CADASTRADO.getCodigo(), exception.getCodigo());
        verify(gateway, never()).save(any());
    }

    @Test
    void findById_Success() {
        when(gateway.findById(1L)).thenReturn(cliente);

        Cliente result = clienteUseCases.findById(1L);

        assertEquals(cliente, result);
        verify(gateway).findById(1L);
    }

    @Test
    void findByEmail_ClienteExists_Success() {
        when(gateway.findByEmail("joao@email.com")).thenReturn(cliente);

        Cliente result = clienteUseCases.findByEmail("joao@email.com");

        assertEquals(cliente, result);
        verify(gateway).findByEmail("joao@email.com");
    }

    @Test
    void findByEmail_ClienteNotFound_ThrowsValidacaoNotFoundException() {
        when(gateway.findByEmail(anyString())).thenReturn(null);

        ValidacaoNotFoundException exception = assertThrows(ValidacaoNotFoundException.class, 
            () -> clienteUseCases.findByEmail("joao@email.com"));

        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getCodigo(), exception.getCodigo());
    }

    @Test
    void findByEmail_GatewayThrowsException_ThrowsValidacaoRuntimeException() {
        when(gateway.findByEmail(anyString())).thenThrow(new RuntimeException());

        ValidacaoRuntimeException exception = assertThrows(ValidacaoRuntimeException.class, 
            () -> clienteUseCases.findByEmail("joao@email.com"));

        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO.getDescricao(), exception.getDescricao());
    }

    @Test
    void findByCpf_ClienteExists_Success() {
        when(gateway.findByCpf("12345678901")).thenReturn(cliente);

        Cliente result = clienteUseCases.findByCpf("12345678901");

        assertEquals(cliente, result);
        verify(gateway).findByCpf("12345678901");
    }

    @Test
    void findByCpf_ClienteNotFound_ThrowsValidacaoNotFoundException() {
        when(gateway.findByCpf(anyString())).thenReturn(null);

        ValidacaoNotFoundException exception = assertThrows(ValidacaoNotFoundException.class, 
            () -> clienteUseCases.findByCpf("12345678901"));

        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getCodigo(), exception.getCodigo());
    }

    @Test
    void findByCpf_GatewayThrowsException_ThrowsValidacaoRuntimeException() {
        when(gateway.findByCpf(anyString())).thenThrow(new RuntimeException());

        ValidacaoRuntimeException exception = assertThrows(ValidacaoRuntimeException.class, 
            () -> clienteUseCases.findByCpf("12345678901"));

        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO.getDescricao(), exception.getDescricao());
    }

    @Test
    void findAll_Success() {
        List<Cliente> clientes = Arrays.asList(cliente, new Cliente());
        when(gateway.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteUseCases.findAll();

        assertEquals(2, result.size());
        verify(gateway).findAll();
    }

    @Test
    void deleteByCpf_ClienteExists_Success() {
        when(gateway.findByCpf("12345678901")).thenReturn(cliente);

        assertDoesNotThrow(() -> clienteUseCases.deleteByCpf("12345678901"));

        verify(gateway).deleteByCpf("12345678901");
    }

    @Test
    void deleteByCpf_ClienteNotFound_ThrowsValidacaoNotFoundException() {
        when(gateway.findByCpf(anyString())).thenReturn(null);

        ValidacaoNotFoundException exception = assertThrows(ValidacaoNotFoundException.class, 
            () -> clienteUseCases.deleteByCpf("12345678901"));

        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getCodigo(), exception.getCodigo());
        verify(gateway, never()).deleteByCpf(anyString());
    }

    @Test
    void deleteByCpf_GatewayThrowsException_ThrowsValidacaoRuntimeException() {
        when(gateway.findByCpf(anyString())).thenReturn(cliente);
        doThrow(new RuntimeException()).when(gateway).deleteByCpf(anyString());

        ValidacaoRuntimeException exception = assertThrows(ValidacaoRuntimeException.class, 
            () -> clienteUseCases.deleteByCpf("12345678901"));

        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO.getDescricao(), exception.getDescricao());
    }

    @Test
    void deleteByEmail_ClienteExists_Success() {
        when(gateway.findByEmail("joao@email.com")).thenReturn(cliente);

        assertDoesNotThrow(() -> clienteUseCases.deleteByEmail("joao@email.com"));

        verify(gateway).deleteByEmail("joao@email.com");
    }

    @Test
    void deleteByEmail_ClienteNotFound_ThrowsValidacaoNotFoundException() {
        when(gateway.findByEmail(anyString())).thenReturn(null);

        ValidacaoNotFoundException exception = assertThrows(ValidacaoNotFoundException.class, 
            () -> clienteUseCases.deleteByEmail("joao@email.com"));

        assertEquals(ValidacaoEnum.CLIENTE_NAO_ENCONTRADO.getCodigo(), exception.getCodigo());
        verify(gateway, never()).deleteByEmail(anyString());
    }

    @Test
    void deleteByEmail_GatewayThrowsException_ThrowsValidacaoRuntimeException() {
        when(gateway.findByEmail(anyString())).thenReturn(cliente);
        doThrow(new RuntimeException()).when(gateway).deleteByEmail(anyString());

        ValidacaoRuntimeException exception = assertThrows(ValidacaoRuntimeException.class, 
            () -> clienteUseCases.deleteByEmail("joao@email.com"));

        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO.getDescricao(), exception.getDescricao());
    }
}