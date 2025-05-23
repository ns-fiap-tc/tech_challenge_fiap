package br.com.fiap.lanchonete.business.adapter.controller;

import br.com.fiap.lanchonete.business.adapter.gateway.PagamentoGateway;
import br.com.fiap.lanchonete.business.common.dto.PagamentoDto;
import br.com.fiap.lanchonete.business.common.mapper.PagamentoMapper;
import br.com.fiap.lanchonete.business.common.persistence.PagamentoRepository;
import br.com.fiap.lanchonete.business.core.domain.Pagamento;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import br.com.fiap.lanchonete.business.core.usecase.PagamentoUseCases;
import br.com.fiap.lanchonete.business.core.usecase.impl.PagamentoUseCasesImpl;
import java.util.List;

public class PagamentoController {
    private static final PagamentoMapper MAPPER = PagamentoMapper.INSTANCE;
    private final PagamentoUseCases useCase;

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        useCase = new PagamentoUseCasesImpl(new PagamentoGateway(pagamentoRepository));
    }

    PagamentoUseCases getUseCases() {
        return this.useCase;
    }

    public Pagamento save(Pagamento pagamento) {
        return useCase.save(pagamento);
    }

    public Pagamento findById(Long id) {
        return useCase.findById(id);
    }

    public List<Pagamento> findAll() {
        return useCase.findAll();
    }

    public void deleteById(Long id) {
        useCase.deleteById(id);
    }

    public void updateStatus(Long pedidoId, PagamentoStatus status) {
        useCase.updateStatus(pedidoId, status);
    }

    public PagamentoDto findByPedidoId(Long pedidoId) {
        return MAPPER.toDto(useCase.findByPedidoId(pedidoId));
    }
}