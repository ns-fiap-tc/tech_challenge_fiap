package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.PedidoGateway;
import br.com.fiap.lanchonete.business.adapter.presenter.PedidoPresenter;
import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pedido.commons.dto.PedidoDto;
import br.com.fiap.lanchonete.business.common.mapper.PedidoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PedidoRepository;
import br.com.fiap.lanchonete.business.common.queue.MessageProducer;
import br.com.fiap.lanchonete.pedido.commons.domain.PedidoStatus;
import br.com.fiap.lanchonete.business.core.usecase.PedidoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.PedidoUseCasesImpl;
import java.util.List;

public class PedidoController {
    private static final PedidoMapper MAPPER = PedidoMapper.INSTANCE;
    private final PedidoUseCases useCase;
    private final PedidoPresenter presenter;

    public PedidoController(
            PedidoRepository pedidoRepository,
            MessageProducer messageProducer,
            PagamentoServiceClient pagamentoServiceClient,
            CategoriaServiceClient categoriaServiceClient,
            ProdutoServiceClient produtoServiceClient,
            OrdemServicoController ordemServicoController)
    {
        useCase = new PedidoUseCasesImpl(
                new PedidoGateway(pedidoRepository),
                messageProducer,
                pagamentoServiceClient,
                categoriaServiceClient,
                produtoServiceClient,
                ordemServicoController.getUseCases());
        presenter = new PedidoPresenter();
    }

    public PedidoDto create(PedidoDto pedidoDto) {
        return presenter.toDto(useCase.create(pedidoDto));
    }

    public PedidoDto update(PedidoDto pedidoDto) {
        return presenter.toDto(useCase.update(pedidoDto));
    }

    public List<PedidoDto> findAllOrdered() {
        return presenter.mapToDto(useCase.findAllOrdered());
    }

    public List<PedidoDto> findByStatus(PedidoStatus status) {
        return presenter.mapToDto(useCase.findByStatus(status));
    }

    public PedidoDto findById(long id) {
        return presenter.toDto(useCase.findById(id));
    }

    public List<PedidoDto> findByCliente(long clienteId) {
        return presenter.mapToDto(useCase.findByCliente(clienteId));
    }

    public void updateStatus(Long id, PedidoStatus status) {
        useCase.updateStatus(id, status);
    }

    public void updatePagamentoStatus(Long pedidoId, PagamentoStatus status) {
        useCase.updatePagamentoStatus(pedidoId, status);
    }
}