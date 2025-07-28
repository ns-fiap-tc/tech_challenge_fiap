package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.gateway.OrdemServicoGateway;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoUseCasesImplTest {

    @Mock
    private OrdemServicoGateway gateway;

    @InjectMocks
    private OrdemServicoUseCasesImpl ordemServicoUseCases;

    private OrdemServico ordemServico;

    @BeforeEach
    void setUp() {
        ordemServico = new OrdemServico();
        ordemServico.setId(1L);
        ordemServico.setNome("Hambúrguer");
        ordemServico.setQuantidade(2);
        ordemServico.setStatus(OrdemServicoStatus.AGUARDANDO);
        ordemServico.setPedidoId(10L);
        ordemServico.setPedidoItemId(5L);
        ordemServico.setProdutoId(3L);
    }

    @Test
    void save_Success() {
        when(gateway.save(any(OrdemServico.class))).thenReturn(ordemServico);

        OrdemServico result = ordemServicoUseCases.save(ordemServico);

        assertNotNull(result);
        assertEquals(ordemServico.getId(), result.getId());
        assertEquals(ordemServico.getNome(), result.getNome());
        verify(gateway).save(ordemServico);
    }

    @Test
    void updateStatus_Success() {
        Long id = 1L;
        OrdemServicoStatus status = OrdemServicoStatus.PRODUCAO;

        ordemServicoUseCases.updateStatus(id, status);

        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        verify(gateway).updateStatus(eq(id), eq(status), dateCaptor.capture());
        
        Date capturedDate = dateCaptor.getValue();
        assertNotNull(capturedDate);
        assertTrue(capturedDate.getTime() <= System.currentTimeMillis());
    }

    @Test
    void findByPedidoId_Success() {
        Long pedidoId = 10L;
        List<OrdemServico> ordens = Arrays.asList(ordemServico, new OrdemServico());
        when(gateway.findByPedidoId(pedidoId)).thenReturn(ordens);

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoId(pedidoId);

        assertEquals(2, result.size());
        verify(gateway).findByPedidoId(pedidoId);
    }

    @Test
    void findByPedidoId_EmptyList() {
        Long pedidoId = 10L;
        when(gateway.findByPedidoId(pedidoId)).thenReturn(Arrays.asList());

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoId(pedidoId);

        assertTrue(result.isEmpty());
        verify(gateway).findByPedidoId(pedidoId);
    }

    @Test
    void findByPedidoIdStatus_Success() {
        Long pedidoId = 10L;
        OrdemServicoStatus status = OrdemServicoStatus.AGUARDANDO;
        List<OrdemServico> ordens = Arrays.asList(ordemServico);
        when(gateway.findByPedidoIdStatus(pedidoId, status)).thenReturn(ordens);

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoIdStatus(pedidoId, status);

        assertEquals(1, result.size());
        assertEquals(ordemServico, result.get(0));
        verify(gateway).findByPedidoIdStatus(pedidoId, status);
    }

    @Test
    void findByPedidoIdStatus_EmptyList() {
        Long pedidoId = 10L;
        OrdemServicoStatus status = OrdemServicoStatus.FINALIZADO;
        when(gateway.findByPedidoIdStatus(pedidoId, status)).thenReturn(Arrays.asList());

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoIdStatus(pedidoId, status);

        assertTrue(result.isEmpty());
        verify(gateway).findByPedidoIdStatus(pedidoId, status);
    }

    @Test
    void findByPedidoItemId_Success() {
        Long pedidoItemId = 5L;
        List<OrdemServico> ordens = Arrays.asList(ordemServico);
        when(gateway.findByPedidoItemId(pedidoItemId)).thenReturn(ordens);

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoItemId(pedidoItemId);

        assertEquals(1, result.size());
        assertEquals(ordemServico, result.get(0));
        verify(gateway).findByPedidoItemId(pedidoItemId);
    }

    @Test
    void findByPedidoItemId_EmptyList() {
        Long pedidoItemId = 5L;
        when(gateway.findByPedidoItemId(pedidoItemId)).thenReturn(Arrays.asList());

        List<OrdemServico> result = ordemServicoUseCases.findByPedidoItemId(pedidoItemId);

        assertTrue(result.isEmpty());
        verify(gateway).findByPedidoItemId(pedidoItemId);
    }
}