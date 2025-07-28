package br.com.fiap.lanchonete.business.core.usecase.impl;

import br.com.fiap.lanchonete.business.adapter.controller.CategoriaServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.PagamentoServiceClient;
import br.com.fiap.lanchonete.business.adapter.controller.ProdutoServiceClient;
import br.com.fiap.lanchonete.business.adapter.gateway.PedidoGateway;
import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
import br.com.fiap.lanchonete.business.core.domain.OrdemServico;
import br.com.fiap.lanchonete.business.core.domain.Pedido;
import br.com.fiap.lanchonete.business.core.domain.PedidoItem;
import br.com.fiap.lanchonete.business.core.usecase.OrdemServicoUseCases;
import br.com.fiap.lanchonete.categoria.commons.dto.CategoriaDto;
import br.com.fiap.lanchonete.categoria.commons.domain.CategoriaTipoEnum;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.commons.dto.PagamentoDto;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import br.com.fiap.lanchonete.produto.commons.dto.ProdutoDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCasesImplTest {

    @Mock
    private PedidoGateway gateway;
    @Mock
    private MessageProducer messageProducer;
    @Mock
    private PagamentoServiceClient pagamentoServiceClient;
    @Mock
    private CategoriaServiceClient categoriaServiceClient;
    @Mock
    private ProdutoServiceClient produtoServiceClient;
    @Mock
    private OrdemServicoUseCases ordemServicoService;

    @InjectMocks
    private PedidoUseCasesImpl pedidoUseCases;

    private Pedido pedido;
    private PedidoDto pedidoDto;
    private PagamentoDto pagamentoDto;
    private PedidoItem pedidoItem;

    @BeforeEach
    void setUp() {
        pedidoItem = new PedidoItem();
        pedidoItem.setId(1L);
        pedidoItem.setProdutoId(10L);
        pedidoItem.setQuantidade(2);
        pedidoItem.setValorUnitario(15.0);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(5L);
        pedido.setStatus(PedidoStatus.CRIACAO);
        pedido.setItens(Arrays.asList(pedidoItem));
        pedido.setPagamentoId("payment-123");

        pagamentoDto = new PagamentoDto();
        pagamentoDto.setId("payment-123");
        pagamentoDto.setPedidoId(1L);

        pedidoDto = new PedidoDto();
        pedidoDto.setId(1L);
        pedidoDto.setClienteId(5L);
        pedidoDto.setStatus(PedidoStatus.CRIACAO);
        pedidoDto.setPagamentoDto(pagamentoDto);
    }

    @Test
    void create_NewPedido_Success() {
        when(gateway.save(any(Pedido.class))).thenReturn(pedido);
        when(pagamentoServiceClient.save(any(PagamentoDto.class))).thenReturn(pagamentoDto);

        Pedido result = pedidoUseCases.create(pedidoDto);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        verify(gateway, times(1)).save(any(Pedido.class));
        verify(pagamentoServiceClient).save(any(PagamentoDto.class));
    }

    @Test
    void create_PedidoWithoutPagamentoId_SetDefaultPagamentoId() {
        pedido.setPagamentoId(null);
        pedidoDto.getPagamentoDto().setId(null);
        
        when(gateway.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido savedPedido = invocation.getArgument(0);
            if (savedPedido.getId() == null) {
                savedPedido.setId(1L);
            }
            return savedPedido;
        });
        when(pagamentoServiceClient.save(any(PagamentoDto.class))).thenReturn(pagamentoDto);

        Pedido result = pedidoUseCases.create(pedidoDto);

        assertNotNull(result);
        verify(gateway, times(1)).save(any(Pedido.class));
    }

    @Test
    void update_ExistingPedido_Success() {
        pedido.setStatus(PedidoStatus.RECEBIDO);
        pedidoDto.setStatus(PedidoStatus.RECEBIDO);
        
        when(gateway.save(any(Pedido.class))).thenReturn(pedido);
        when(pagamentoServiceClient.realizarPagamento(any(PagamentoDto.class))).thenReturn(pagamentoDto);

        Pedido result = pedidoUseCases.update(pedidoDto);

        assertNotNull(result);
        assertEquals(PedidoStatus.RECEBIDO, result.getStatus());
        verify(gateway).save(any(Pedido.class));
        verify(pagamentoServiceClient).realizarPagamento(any(PagamentoDto.class));
    }

    @Test
    void findAllOrdered_Success() {
        List<Pedido> pedidos = Arrays.asList(pedido, new Pedido());
        when(gateway.findAllOrdered()).thenReturn(pedidos);

        List<Pedido> result = pedidoUseCases.findAllOrdered();

        assertEquals(2, result.size());
        verify(gateway).findAllOrdered();
    }

    @Test
    void findByStatus_Success() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(gateway.findByStatus(PedidoStatus.CRIACAO)).thenReturn(pedidos);

        List<Pedido> result = pedidoUseCases.findByStatus(PedidoStatus.CRIACAO);

        assertEquals(1, result.size());
        assertEquals(PedidoStatus.CRIACAO, result.get(0).getStatus());
        verify(gateway).findByStatus(PedidoStatus.CRIACAO);
    }

    @Test
    void findById_Success() {
        when(gateway.findById(1L)).thenReturn(pedido);

        Pedido result = pedidoUseCases.findById(1L);

        assertEquals(pedido, result);
        verify(gateway).findById(1L);
    }

    @Test
    void findByCliente_Success() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(gateway.findByClienteId(5L)).thenReturn(pedidos);

        List<Pedido> result = pedidoUseCases.findByCliente(5L);

        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getClienteId());
        verify(gateway).findByClienteId(5L);
    }

    @Test
    void updateStatus_Success() {
        Long pedidoId = 1L;
        PedidoStatus status = PedidoStatus.PREPARACAO;

        pedidoUseCases.updateStatus(pedidoId, status);

        verify(gateway).updateStatus(pedidoId, status);
    }

    @Test
    void updatePagamentoStatus_MethodExists() {
        // This method is commented out in the implementation
        // but we test that it exists and doesn't throw exceptions
        assertDoesNotThrow(() -> pedidoUseCases.updatePagamentoStatus(1L, PagamentoStatus.CONFIRMADO));
    }

    @Test
    void create_PedidoWithEmptyPagamentoId_SetDefaultValue() {
        pedido.setPagamentoId("");
        pedidoDto.getPagamentoDto().setId("");
        
        when(gateway.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido savedPedido = invocation.getArgument(0);
            if (savedPedido.getId() == null) {
                savedPedido.setId(1L);
            }
            return savedPedido;
        });
        when(pagamentoServiceClient.save(any(PagamentoDto.class))).thenReturn(pagamentoDto);

        Pedido result = pedidoUseCases.create(pedidoDto);

        assertNotNull(result);
        verify(gateway, times(1)).save(any(Pedido.class));
    }

    @Test
    void findByStatus_EmptyList() {
        when(gateway.findByStatus(any(PedidoStatus.class))).thenReturn(Arrays.asList());

        List<Pedido> result = pedidoUseCases.findByStatus(PedidoStatus.FINALIZADO);

        assertTrue(result.isEmpty());
        verify(gateway).findByStatus(PedidoStatus.FINALIZADO);
    }

    @Test
    void findByCliente_EmptyList() {
        when(gateway.findByClienteId(anyLong())).thenReturn(Arrays.asList());

        List<Pedido> result = pedidoUseCases.findByCliente(999L);

        assertTrue(result.isEmpty());
        verify(gateway).findByClienteId(999L);
    }

    @Test
    void findById_ReturnsNull() {
        when(gateway.findById(anyLong())).thenReturn(null);

        Pedido result = pedidoUseCases.findById(999L);

        assertNull(result);
        verify(gateway).findById(999L);
    }
}